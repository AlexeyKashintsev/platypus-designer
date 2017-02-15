package com.eas.client.model.gui.edits;

import com.eas.client.model.Entity;
import com.eas.client.model.Relation;

/**
 *
 * @author mg
 */
public class NewRelationEdit<E extends Entity<?, ?, E>> extends DeleteRelationEdit<E> {

    public NewRelationEdit(Relation<E> aRel) {
        super(aRel);
    }

    @Override
    protected void redoWork() {
        super.undoWork();
    }

    @Override
    protected void undoWork() {
        super.redoWork();
    }
}