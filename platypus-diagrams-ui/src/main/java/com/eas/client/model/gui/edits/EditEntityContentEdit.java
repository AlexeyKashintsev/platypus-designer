package com.eas.client.model.gui.edits;

/**
 *
 * @author mg
 */
public class EditEntityContentEdit extends DatamodelEdit {

    public EditEntityContentEdit() {
        super();
    }

    @Override
    public boolean isNeedConnectors2Reroute() {
        return true;
    }

    @Override
    protected void redoWork() {
    }

    @Override
    protected void undoWork() {
    }
}