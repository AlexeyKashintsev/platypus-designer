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
@ProjectCustomizer.CompositeCategoryProvider.Registration(category = "platypusClient", categoryLabel = "#platypusClient", projectType = "org-netbeans-modules-platypus", position = 14)
public class PlatypusClientCategoryProvider implements ProjectCustomizer.CompositeCategoryProvider {

    @Override
    public ProjectCustomizer.Category createCategory(Lookup lkp) {
        return ProjectCustomizer.Category.create("platypusClient", NbBundle.getMessage(PlatypusProjectCustomizerProvider.class, "platypusClient"), null, new ProjectCustomizer.Category[]{});
    }

    @Override
    public JComponent createComponent(ProjectCustomizer.Category ctgr, Lookup lkp) {
        return new PlatypusClientCustomizer(lkp.lookup(PlatypusProject.class));
    }
}
