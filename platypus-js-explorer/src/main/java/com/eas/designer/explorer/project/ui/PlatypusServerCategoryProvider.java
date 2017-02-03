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
@ProjectCustomizer.CompositeCategoryProvider.Registration(category = "platypusServer", categoryLabel = "#platypusServer", projectType = "org-netbeans-modules-platypus", position = 13)
public class PlatypusServerCategoryProvider implements ProjectCustomizer.CompositeCategoryProvider {

    @Override
    public ProjectCustomizer.Category createCategory(Lookup lkp) {
        return ProjectCustomizer.Category.create("platypusServer", NbBundle.getMessage(PlatypusProjectCustomizerProvider.class, "platypusServer"), null, new ProjectCustomizer.Category[]{});
    }

    @Override
    public JComponent createComponent(ProjectCustomizer.Category ctgr, Lookup lkp) {
        return new PlatypusServerCustomizer(lkp.lookup(PlatypusProject.class));
    }
}
