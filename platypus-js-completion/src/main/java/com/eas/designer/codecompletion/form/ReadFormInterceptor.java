package com.eas.designer.codecompletion.form;

import java.util.regex.Pattern;
import org.netbeans.modules.javascript2.model.api.JsObject;
import org.netbeans.modules.javascript2.model.spi.FunctionInterceptor;
import org.netbeans.modules.javascript2.model.spi.ModelElementFactory;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectNotFoundException;

/**
 *
 * @author mg
 */
@FunctionInterceptor.Registration
public class ReadFormInterceptor extends FormInterceptor {

    private static final String READ_FORM_NAME = "readForm";
    private static final Pattern PATTERN = Pattern.compile(".+\\." + READ_FORM_NAME);

    public ReadFormInterceptor() {
        super(READ_FORM_NAME);
    }

    @Override
    public Pattern getNamePattern() {
        return PATTERN;
    }

    @Override
    protected void fillJsForm(FileObject fo, ModelElementFactory factory, int loadFormOffset, JsObject jsForm) throws DataObjectNotFoundException, Exception {
        // TODO: Fill the read form with widgets if form's content is passed in as a string literal
    }

}
