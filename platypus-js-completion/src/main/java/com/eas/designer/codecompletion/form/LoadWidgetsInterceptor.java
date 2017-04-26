package com.eas.designer.codecompletion.form;

import com.bearsoft.org.netbeans.modules.form.FormModel;
import com.bearsoft.org.netbeans.modules.form.FormUtils;
import com.bearsoft.org.netbeans.modules.form.FormUtils.Panel;
import com.bearsoft.org.netbeans.modules.form.PlatypusFormDataObject;
import com.bearsoft.org.netbeans.modules.form.PlatypusFormSupport;
import com.bearsoft.org.netbeans.modules.form.RADComponent;
import com.bearsoft.org.netbeans.modules.form.RADVisualContainer;
import com.bearsoft.org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import com.eas.designer.codecompletion.InterceptorUtils;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.javascript2.model.api.JsObject;
import org.netbeans.modules.javascript2.model.spi.FunctionInterceptor;
import org.netbeans.modules.javascript2.model.spi.ModelElementFactory;
import org.netbeans.modules.javascript2.types.api.TypeUsage;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;

/**
 *
 * @author mg
 */
@FunctionInterceptor.Registration
public class LoadWidgetsInterceptor extends FormInterceptor {

    private static final String LOAD_WIDGETS_NAME = "loadWidgets";
    private static final Pattern PATTERN = Pattern.compile(".+\\." + LOAD_WIDGETS_NAME);

    public LoadWidgetsInterceptor() {
        super(LOAD_WIDGETS_NAME);
    }

    @Override
    public Pattern getNamePattern() {
        return PATTERN;
    }

    @Override
    protected void fillJsForm(JsObject jsForm, int loadFormOffset, ModelElementFactory factory) throws IOException, Exception {
        fillWidgetsFromFile(jsForm.getFileObject(), factory, loadFormOffset, jsForm);
    }
}
