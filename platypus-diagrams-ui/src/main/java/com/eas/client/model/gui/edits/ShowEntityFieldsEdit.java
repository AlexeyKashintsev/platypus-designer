package com.eas.client.model.gui.edits;

import com.eas.client.model.Entity;

/**
 *
 * @author mg
 */
public class ShowEntityFieldsEdit<E extends Entity<?, ?, E>> extends HideEntityFieldsEdit<E>
{
    public ShowEntityFieldsEdit()
    {
        super();
    }

    public ShowEntityFieldsEdit(E aEntity)
    {
        super(aEntity);
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
