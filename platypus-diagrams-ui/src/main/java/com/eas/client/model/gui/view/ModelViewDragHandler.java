package com.eas.client.model.gui.view;

import com.eas.client.model.gui.view.model.ModelView;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author mg
 */
public class ModelViewDragHandler extends TransferHandler {

    protected ModelView<?, ?> mView = null;

    public ModelViewDragHandler(ModelView<?, ?> aModelView) {
        super();
        mView = aModelView;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        super.exportDone(source, data, action);
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return false;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE | COPY;
    }

    @Override
    public boolean importData(TransferSupport support) {
        return false;
    }
}
