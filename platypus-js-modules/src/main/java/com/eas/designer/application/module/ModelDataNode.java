package com.eas.designer.application.module;

import com.eas.designer.explorer.PlatypusDataObject;
import javax.swing.Action;
import org.openide.loaders.DataNode;
import org.openide.nodes.FilterNode;
import org.openide.util.actions.SystemAction;

/**
 * Node of projects view of designer
 * @author mg
 */
public class ModelDataNode extends FilterNode {

    private static final String MODEL_ICON_BASE = ModelDataNode.class.getPackage().getName().replace('.', '/') + "/model.png"; // NOI18N

    public ModelDataNode(PlatypusDataObject fdo) {
        this(new DataNode(fdo, Children.LEAF));
    }

    private ModelDataNode(DataNode orig) {
        super(orig);
        orig.setIconBaseWithExtension(MODEL_ICON_BASE);
    }

    @Override
    public Action getPreferredAction() {
        return new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                PlatypusModelSupport supp = getLookup().lookup(PlatypusModelSupport.class);
                supp.open();
            }
        };
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] javaActions = super.getActions(context);
        Action[] formActions = new Action[javaActions.length + 3];
        formActions[0] = SystemAction.get(org.openide.actions.OpenAction.class);
        formActions[1] = SystemAction.get(org.openide.actions.EditAction.class);
        formActions[2] = null;
        System.arraycopy(javaActions, 0, formActions, 3, javaActions.length);
        return formActions;
    }
}
