package com.eas.designer.application.module.nodes;

import com.eas.client.model.Relation;
import com.eas.client.model.application.ApplicationDbEntity;
import com.eas.client.model.application.ReferenceRelation;
import com.eas.client.model.gui.DatamodelDesignUtils;
import com.eas.client.model.gui.view.entities.EntityView;
import com.eas.designer.datamodel.nodes.RelationNode;
import java.beans.PropertyChangeEvent;
import java.util.List;
import javax.swing.Action;
import org.openide.actions.PropertiesAction;
import org.openide.awt.UndoRedo;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import com.eas.client.model.gui.edits.CollectionPropertyNameEdit;
import com.eas.client.model.gui.edits.ScalarPropertyNameEdit;
import com.eas.client.model.gui.view.ScalarCollectionView;
import java.util.MissingResourceException;
import org.openide.util.NbBundle;

/**
 *
 * @author mg
 */
public class ReferenceRelationNode extends RelationNode<ApplicationDbEntity, Relation<ApplicationDbEntity>> {

    private final PropertySupport.Reflection<String> referenceNameProp;
    private final PropertySupport.Reflection<String> collectionNameProp;

    public ReferenceRelationNode(ReferenceRelation<ApplicationDbEntity> aRelation, UndoRedo.Manager aUndoReciever, Lookup aLookup) throws Exception {
        super(aRelation, aUndoReciever, aLookup);
        referenceNameProp = new PropertySupport.Reflection<>(aRelation, String.class, ReferenceRelation.SCALAR_PROPERTY_NAME);
        referenceNameProp.setName(ReferenceRelation.SCALAR_PROPERTY_NAME);
        referenceNameProp.setDisplayName(NbBundle.getMessage(ReferenceRelationNode.class, "referenceName"));
        nameToProperty.put(ReferenceRelation.SCALAR_PROPERTY_NAME, referenceNameProp);
        collectionNameProp = new PropertySupport.Reflection<>(aRelation, String.class, ReferenceRelation.COLLECTION_PROPERTY_NAME);
        collectionNameProp.setName(ReferenceRelation.COLLECTION_PROPERTY_NAME);
        collectionNameProp.setDisplayName(NbBundle.getMessage(ReferenceRelationNode.class, "collectionName"));
        nameToProperty.put(ReferenceRelation.COLLECTION_PROPERTY_NAME, collectionNameProp);
        updateShortDescription();
    }

    private void updateShortDescription() throws MissingResourceException {
        ReferenceRelation<ApplicationDbEntity> aRelation = (ReferenceRelation<ApplicationDbEntity>) relation;
        String leName = aRelation.getLeftEntity().getName();
        if (leName == null) {
            leName = aRelation.getLeftEntity().getTitle();
            if (leName == null || leName.isEmpty()) {
                leName = DatamodelDesignUtils.getLocalizedString("noName");
            }
        }
        String lfName = aRelation.getLeftField().getName();
        String reName = aRelation.getRightEntity().getName();
        if (reName == null) {
            reName = aRelation.getRightEntity().getTitle();
            if (reName == null || reName.isEmpty()) {
                reName = DatamodelDesignUtils.getLocalizedString("noName");
            }
        }
        String rfName = aRelation.getRightField().getName();
        setShortDescription(NbBundle.getMessage(ScalarCollectionView.class, "ScalarCollectionView.lblDescription.text", leName, lfName, reName, rfName));
        referenceNameProp.setShortDescription(NbBundle.getMessage(ScalarCollectionView.class, "ScalarCollectionView.lblScalar.text", reName, leName));
        collectionNameProp.setShortDescription(NbBundle.getMessage(ScalarCollectionView.class, "ScalarCollectionView.lblCollection.text", leName, reName));
    }

    @Override
    public String getName() {
        return EntityView.getCheckedEntityTitle(relation.getLeftEntity()) + (relation.getLeftField() != null ? "." + relation.getLeftField().getName() : "") + " -> " + EntityView.getCheckedEntityTitle(relation.getRightEntity());
    }

    @Override
    public void processNodePropertyChange(PropertyChangeEvent nodeEvent) {
        if (ReferenceRelation.SCALAR_PROPERTY_NAME.equals(nodeEvent.getPropertyName())) {
            ScalarPropertyNameEdit edit = new ScalarPropertyNameEdit((ReferenceRelation<ApplicationDbEntity>) relation, (String) nodeEvent.getOldValue(), (String) nodeEvent.getNewValue());
            undoReciever.addEdit(edit);
        } else if (ReferenceRelation.COLLECTION_PROPERTY_NAME.equals(nodeEvent.getPropertyName())) {
            CollectionPropertyNameEdit edit = new CollectionPropertyNameEdit((ReferenceRelation<ApplicationDbEntity>) relation, (String) nodeEvent.getOldValue(), (String) nodeEvent.getNewValue());
            undoReciever.addEdit(edit);
        }
        super.processNodePropertyChange(nodeEvent);
        updateShortDescription();
    }

    @Override
    protected void fillActions(List<Action> aList) {
        aList.add(PropertiesAction.get(PropertiesAction.class));
    }

    /**
     * Provides access for firing property changes
     *
     * @param name property name
     * @param oldValue old value of the property
     * @param newValue new value of the property
     */
    public void firePropertyChangeHelper(String name,
            Object oldValue, Object newValue) {
        super.firePropertyChange(name, oldValue, newValue);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        Sheet.Set pSet = sheet.get(Sheet.PROPERTIES);
        pSet.put(referenceNameProp);
        pSet.put(collectionNameProp);
        sheet.put(pSet);
        return sheet;
    }
}
