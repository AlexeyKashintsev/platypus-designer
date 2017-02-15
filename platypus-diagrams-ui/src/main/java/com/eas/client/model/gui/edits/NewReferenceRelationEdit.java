package com.eas.client.model.gui.edits;

import com.eas.client.model.application.ApplicationEntity;
import com.eas.client.model.application.ReferenceRelation;

/**
 *
 * @author mg
 */
public class NewReferenceRelationEdit<E extends ApplicationEntity<?, ?, E>> extends DeleteReferenceRelationEdit<E> {

    public NewReferenceRelationEdit(ReferenceRelation<E> aRel) {
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