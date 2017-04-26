package com.eas.designer.codecompletion.form;

import java.io.IOException;
import java.util.regex.Pattern;
import org.netbeans.modules.javascript2.model.api.JsObject;
import org.netbeans.modules.javascript2.model.spi.FunctionInterceptor;
import org.netbeans.modules.javascript2.model.spi.ModelElementFactory;

/**
 *
 * @author mg
 */
@FunctionInterceptor.Registration
public class LoadFormInterceptor extends FormInterceptor {

    private static final String LOAD_FORM_NAME = "loadForm";
    private static final Pattern PATTERN = Pattern.compile(".+\\." + LOAD_FORM_NAME);

    public LoadFormInterceptor() {
        super(LOAD_FORM_NAME);
    }

    protected LoadFormInterceptor(String aInterceptedName) {
        super(aInterceptedName);
    }

    @Override
    public Pattern getNamePattern() {
        return PATTERN;
    }

    @Override
    protected void fillJsForm(JsObject jsForm, int loadFormOffset, ModelElementFactory factory) throws IOException, Exception {
        fillWindowType(jsForm.getFileObject(), loadFormOffset, factory, jsForm);
        fillWidgetsFromFile(jsForm.getFileObject(), factory, loadFormOffset, jsForm);
    }
}
