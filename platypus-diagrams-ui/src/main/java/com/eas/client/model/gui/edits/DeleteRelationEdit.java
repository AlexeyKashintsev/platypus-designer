package com.eas.client.model.gui.edits;

import com.eas.client.model.Entity;
import com.eas.client.model.Model;
import com.eas.client.model.Relation;

/**
 *
 * @author mg
 */
public class DeleteRelationEdit<E extends Entity<?, ?, E>> extends DatamodelEdit {

    private Relation<E> relation;

    public DeleteRelationEdit(Relation<E> aRel) {
        super();
        relation = aRel;
    }

    @Override
    public boolean isNeedConnectors2Reroute() {
        return true;
    }

    @Override
    protected void redoWork() {
        Model<E, ?> model = relation.getLeftEntity().getModel();
        if (model != null) {
            model.removeRelation(relation);
        }
    }

    @Override
    protected void undoWork() {
        Model<E, ?> model = relation.getLeftEntity().getModel();
        if (model != null) {
            model.addRelation(getRelation());
        }
    }

    /**
     * @return the relation
     */
    public Relation<E> getRelation() {
        return relation;
    }

    /**
     * @param aValue the relation to set
     */
    public void setRelation(Relation<E> aValue) {
        relation = aValue;
    }
}