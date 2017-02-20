package com.eas.designer.codecompletion.module;

import com.eas.designer.codecompletion.InterceptorUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.javascript2.lexer.api.JsTokenId;
import org.netbeans.modules.javascript2.lexer.api.LexUtilities;
import org.netbeans.modules.javascript2.model.api.JsObject;
import org.netbeans.modules.javascript2.model.spi.FunctionArgument;
import org.netbeans.modules.javascript2.model.spi.FunctionInterceptor;
import org.netbeans.modules.javascript2.model.spi.ModelElementFactory;
import org.netbeans.modules.javascript2.types.api.DeclarationScope;
import org.netbeans.modules.javascript2.types.api.TypeUsage;
import org.netbeans.modules.parsing.api.Snapshot;
import org.openide.filesystems.FileObject;

/**
 *
 * @author mg
 */
public abstract class ModelInterceptor implements FunctionInterceptor {
    
    private static final String MODEL_MODULE_NAME = "application-platypus-model"/*Crazy RequireJsIndexer! It should be datamodel/application-platypus-model */;
    private final String interceptedName;
    
    public ModelInterceptor(String aInterceptedName) {
        super();
        interceptedName = aInterceptedName;
    }

    @Override
    public Collection<TypeUsage> intercept(Snapshot snapshot, String name, JsObject globalObject,
            DeclarationScope scope, ModelElementFactory factory, Collection<FunctionArgument> args) {
        if (!args.isEmpty()) {
            TokenHierarchy<?> th = snapshot.getTokenHierarchy();
            TokenSequence<? extends JsTokenId> ts = (TokenSequence<? extends JsTokenId>) th.tokenSequence();
            FunctionArgument moduleNameArg = args.iterator().next();
            ts.move(moduleNameArg.getOffset());
            if (ts.moveNext()) {
                Token<? extends JsTokenId> loadModelToken = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.IDENTIFIER, JsTokenId.OPERATOR_SEMICOLON));
                if (loadModelToken != null && loadModelToken.id() == JsTokenId.IDENTIFIER && interceptedName.equals(loadModelToken.text().toString())) {
                    int loadModelOffset = loadModelToken.offset(th);
                    Token<? extends JsTokenId> token = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.OPERATOR_ASSIGNMENT, JsTokenId.OPERATOR_SEMICOLON));
                    if (token != null && token.id() == JsTokenId.OPERATOR_ASSIGNMENT) {
                        token = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.IDENTIFIER, JsTokenId.OPERATOR_SEMICOLON,
                                JsTokenId.BRACKET_LEFT_BRACKET, JsTokenId.BRACKET_LEFT_CURLY, JsTokenId.BRACKET_LEFT_PAREN));
                        if (token != null && token.id() == JsTokenId.IDENTIFIER) {
                            String objectName = token.text().toString();
                            JsObject jsModel = ((JsObject) scope).getProperty(objectName);
                            if (jsModel != null) {
                                try {
                                    FileObject fo = jsModel.getFileObject();
                                    Collection<TypeUsage> apiModelTypes = InterceptorUtils.getModuleExposedTypes(fo, loadModelOffset, factory, MODEL_MODULE_NAME);
                                    apiModelTypes.stream().forEach((apiModelType) -> {
                                        jsModel.addAssignment(apiModelType, apiModelType.getOffset());
                                    });
                                    fillJsModel(fo, factory, loadModelOffset, jsModel);
                                } catch (Exception ex) {
                                    Logger.getLogger(ModelInterceptor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        }
        return Collections.emptySet();
    }

    protected abstract void fillJsModel(FileObject fo, ModelElementFactory factory, int loadModelOffset, JsObject jsModel) throws Exception;
}
