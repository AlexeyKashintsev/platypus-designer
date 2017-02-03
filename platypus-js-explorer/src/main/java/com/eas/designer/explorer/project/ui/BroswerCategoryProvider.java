package com.eas.designer.explorer.project.ui;

import com.eas.designer.application.project.PlatypusProject;
import javax.swing.JComponent;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author mg
 */
@ProjectCustomizer.CompositeCategoryProvider.Registration(category = "browserClient", categoryLabel = "#browserClient", projectType = "org-netbeans-modules-platypus", position = 12)
public class BroswerCategoryProvider implements ProjectCustomizer.CompositeCategoryProvider {

    @Override
    public ProjectCustomizer.Category createCategory(Lookup lkp) {
        return ProjectCustomizer.Category.create("browserClient", NbBundle.getMessage(PlatypusProjectCustomizerProvider.class, "browserClient"), null, new ProjectCustomizer.Category[]{});
    }

    @Override
    public JComponent createComponent(ProjectCustomizer.Category ctgr, Lookup lkp) {
        return new BrowserCustomizer(lkp.lookup(PlatypusProject.class));
    }
}
