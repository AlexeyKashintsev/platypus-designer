package com.eas.designer.codecompletion.form;

import com.bearsoft.org.netbeans.modules.form.FormModel;
import com.bearsoft.org.netbeans.modules.form.FormUtils;
import com.bearsoft.org.netbeans.modules.form.PlatypusFormDataObject;
import com.bearsoft.org.netbeans.modules.form.PlatypusFormSupport;
import com.bearsoft.org.netbeans.modules.form.RADComponent;
import com.bearsoft.org.netbeans.modules.form.RADVisualContainer;
import com.bearsoft.org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import com.eas.designer.codecompletion.InterceptorUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.OffsetRange;
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
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;

/**
 *
 * @author mg
 */
public abstract class FormInterceptor implements FunctionInterceptor {
    
    private static final String FORM_MODULE_NAME = "form"/*Crazy RequireJsIndexer! It should be forms/form */;
    private final String interceptedName;
    
    public FormInterceptor(String aInterceptedName) {
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
                Token<? extends JsTokenId> loadFormToken = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.IDENTIFIER, JsTokenId.OPERATOR_SEMICOLON));
                if (loadFormToken != null && loadFormToken.id() == JsTokenId.IDENTIFIER && interceptedName.equals(loadFormToken.text().toString())) {
                    int loadFormOffset = loadFormToken.offset(th);
                    Token<? extends JsTokenId> token = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.OPERATOR_ASSIGNMENT, JsTokenId.OPERATOR_SEMICOLON));
                    if (token != null && token.id() == JsTokenId.OPERATOR_ASSIGNMENT) {
                        token = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.IDENTIFIER, JsTokenId.OPERATOR_SEMICOLON,
                                JsTokenId.BRACKET_LEFT_BRACKET, JsTokenId.BRACKET_LEFT_CURLY, JsTokenId.BRACKET_LEFT_PAREN));
                        if (token != null && token.id() == JsTokenId.IDENTIFIER) {
                            String objectName = token.text().toString();
                            JsObject jsForm = ((JsObject) scope).getProperty(objectName);
                            if (jsForm != null) {
                                try {
                                    fillJsForm(jsForm, loadFormOffset, factory);
                                } catch (Exception ex) {
                                    Logger.getLogger(FormInterceptor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        }
        return Collections.emptySet();
    }

    protected abstract void fillJsForm(JsObject jsForm, int loadFormOffset, ModelElementFactory factory) throws IOException, Exception;

    protected void fillWindowType(FileObject fo, int loadFormOffset, ModelElementFactory factory, JsObject jsForm) throws IOException {
        Collection<TypeUsage> apiFormTypes = InterceptorUtils.getModuleExposedTypes(fo, loadFormOffset, factory, FORM_MODULE_NAME);
        apiFormTypes.stream().forEach((apiFormType) -> {
            jsForm.addAssignment(apiFormType, apiFormType.getOffset());
        });
    }

    protected void fillWidgetsFromFile(FileObject fo, ModelElementFactory factory, int loadFormOffset, JsObject jsForm) throws DataObjectNotFoundException, Exception {
        DataObject dataObject = DataObject.find(fo);
        if (dataObject instanceof PlatypusFormDataObject) {
            PlatypusFormDataObject formDataObject = (PlatypusFormDataObject) dataObject;
            PlatypusFormSupport formContainer = formDataObject.getLookup().lookup(PlatypusFormSupport.class);
            assert formContainer != null;
            if (formContainer.isOpened()) {
                FormModel model = formContainer.getFormModel();
                for (RADComponent<?> radComp : model.getOrderedComponentList()) {
                    if (radComp.getName() != null && !radComp.getName().isEmpty()) {
                        JsObject jsWidget = factory.newObject(jsForm, radComp.getName(), new OffsetRange(loadFormOffset, loadFormOffset), false);
                        String beanCalssShortName = radComp.getBeanClass().getSimpleName();
                        String widgetModuleName;
                        if (FormUtils.Panel.class.getSimpleName().equals(beanCalssShortName)) {
                            RADVisualContainer<?> radCont = (RADVisualContainer<?>) radComp;
                            LayoutSupportManager layoutSupportManager = radCont.getLayoutSupport();
                            if (layoutSupportManager != null && layoutSupportManager.getLayoutDelegate() != null) {
                                Class<?> layoutedContainerClass = FormUtils.getPlatypusConainerClass(layoutSupportManager.getLayoutDelegate().getSupportedClass());
                                widgetModuleName = dashify(layoutedContainerClass.getSimpleName());
                            } else {
                                widgetModuleName = dashify(beanCalssShortName);
                            }
                        } else {
                            widgetModuleName = dashify(beanCalssShortName);
                        }
                        Collection<TypeUsage> apiWidgetTypes = InterceptorUtils.getModuleExposedTypes(fo, loadFormOffset, factory, widgetModuleName);
                        apiWidgetTypes.stream().forEach((apiEntityType) -> {
                            jsWidget.addAssignment(apiEntityType, apiEntityType.getOffset());
                        });
                        jsForm.addProperty(jsWidget.getName(), jsWidget);
                    }
                }
            }
        }
    }

    private static String dashify(String aCamelCaseName) {
        char[] chars = aCamelCaseName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aCamelCaseName.length(); i++) {
            sb.append(Character.toLowerCase(chars[i]));
            if (i < aCamelCaseName.length() - 1 && Character.isLowerCase(chars[i]) && Character.isUpperCase(chars[i + 1])) {
                sb.append('-');
            }
        }
        return sb.toString();
    }
}
