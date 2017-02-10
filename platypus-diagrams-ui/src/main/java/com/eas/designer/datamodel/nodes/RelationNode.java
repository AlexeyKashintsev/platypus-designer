package com.eas.designer.datamodel.nodes;

import com.eas.client.model.Entity;
import com.eas.client.model.Relation;
import com.eas.client.model.gui.view.entities.EntityView;
import com.eas.designer.datamodel.ModelUndoProvider;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import org.openide.actions.DeleteAction;
import org.openide.actions.PropertiesAction;
import org.openide.awt.UndoRedo;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;

/**
 *
 * @author mg
 * @param <E>
 */
public class RelationNode<E extends Entity<?, ?, E>, R extends Relation<E>> extends AbstractNode implements PropertyChangeListener {

    /**
     * The icon for BorderLayout.
     */
    private static final String ICON_URL =
            "com/eas/designer/datamodel/nodes/relation.png"; // NOI18N
    protected Map<String, Node.Property<String>> nameToProperty = new HashMap<>();
    protected R relation;
    protected Action[] actions;
    protected UndoRedo.Manager undoReciever;

    public RelationNode(R aRelation, UndoRedo.Manager aUndoReciever, Lookup aLookup) throws Exception {
        super(Children.LEAF, aLookup);
        relation = aRelation;
        relation.getChangeSupport().addPropertyChangeListener(this);
        undoReciever = aUndoReciever;
    }

    @Override
    public void destroy() throws IOException {
        relation.getChangeSupport().removePropertyChangeListener(this);
        super.destroy();
    }

    @Override
    public Action getPreferredAction() {
        return null;
    }

    @Override
    public Action[] getActions(boolean context) {
        if (actions == null) {
            List<Action> lactions = new ArrayList<>(20);
            fillActions(lactions);
            actions = new Action[lactions.size()];
            lactions.toArray(actions);
        }
        return actions;
    }

    protected void fillActions(List<Action> aList) {
        aList.add(SystemAction.get(DeleteAction.class));
        aList.add(PropertiesAction.get(PropertiesAction.class));
    }

    @Override
    public String getName() {
        return EntityView.getCheckedEntityTitle(relation.getLeftEntity()) + " -> " + EntityView.getCheckedEntityTitle(relation.getRightEntity());
    }

    @Override
    public String getDisplayName() {
        return getName();
    }

    @Override
    public void setDisplayName(String s) {
    }

    @Override
    public String getHtmlDisplayName() {
        return getName();
    }

    public R getRelation() {
        return relation;
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(ICON_URL);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PropertyChangeEvent nodeEvent = convertPropertyChangeEventToNode(evt);
        if (nodeEvent != null) {
            processNodePropertyChange(nodeEvent);
        }
    }

    public PropertyChangeEvent convertPropertyChangeEventToNode(PropertyChangeEvent evt) {
        if (nameToProperty.containsKey(evt.getPropertyName())) {
            return new PropertyChangeEvent(this, evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        }
        return null;
    }

    public void processNodePropertyChange(PropertyChangeEvent evt) {
        // firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        fireDisplayNameChange((String) evt.getOldValue(), (String) evt.getNewValue());
        firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }

    @Override
    public boolean canRename() {
        return true;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = new Sheet();
        Sheet.Set pSet = Sheet.createPropertiesSet();
        sheet.put(pSet);
        return sheet;
    }

    public void reorder(int[] order) {
    }

    public Property<String> getPropertyByName(String name) {
        return nameToProperty.get(name);
    }

    protected UndoRedo.Manager getUndo() {
        return getLookup().lookup(ModelUndoProvider.class).getModelUndo();
    }
}
