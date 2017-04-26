package com.eas.designer.codecompletion.form;

import java.io.IOException;
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
public class ReadWidgetsInterceptor extends FormInterceptor {

    private static final String READ_WIDGETS_NAME = "readWidgets";
    private static final Pattern PATTERN = Pattern.compile(".+\\." + READ_WIDGETS_NAME);

    public ReadWidgetsInterceptor() {
        super(READ_WIDGETS_NAME);
    }

    @Override
    public Pattern getNamePattern() {
        return PATTERN;
    }

    @Override
    protected void fillJsForm(JsObject jsForm, int loadFormOffset, ModelElementFactory factory) throws IOException, Exception {
        // no window expected.
        // fillWidgetsFromLiteral
    }
}
