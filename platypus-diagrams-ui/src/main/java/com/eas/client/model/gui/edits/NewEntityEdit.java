package com.eas.client.model.gui.edits;

import com.eas.client.SqlQuery;
import com.eas.client.model.Entity;
import com.eas.client.model.Model;

/**
 *
 * @author mg
 * @param <E>
 * @param <M>
 */
public class NewEntityEdit<E extends Entity<?, SqlQuery, E>, M extends Model<E, SqlQuery>> extends DeleteEntityEdit<E, M> {

    public NewEntityEdit(M aModel, E aEntity) {
        super(aModel, aEntity);
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
