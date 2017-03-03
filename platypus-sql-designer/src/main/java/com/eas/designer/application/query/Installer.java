package com.eas.designer.application.query;

import com.eas.designer.explorer.InstallerAdapter;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author mg
 */
public class Installer extends InstallerAdapter {

    @Override
    protected Collection<String> friendOfWhom() {
        return Arrays.asList(
                "org.netbeans.modules.db.core"
        );
    }

}
