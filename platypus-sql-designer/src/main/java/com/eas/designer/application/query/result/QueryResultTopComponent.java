package com.eas.designer.application.query.result;

import java.beans.PropertyChangeEvent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.TabbedPaneFactory;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Top component which displays queries results.
 */
@ConvertAsProperties(dtd = "-//com.eas.designer.application.query.result//QueryResult//EN",
        autostore = false)
@TopComponent.Description(preferredID = "QueryResultTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "com.eas.designer.application.query.result.QueryResultTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_QueryResultAction",
        preferredID = "QueryResultTopComponent")
public final class QueryResultTopComponent extends TopComponent {

    public QueryResultTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(QueryResultTopComponent.class, "CTL_QueryResultTopComponent"));
        setToolTipText(NbBundle.getMessage(QueryResultTopComponent.class, "HINT_QueryResultTopComponent"));
        tabPane.addPropertyChangeListener(TabbedPaneFactory.PROP_CLOSE, (PropertyChangeEvent evt) -> {
            try {
                QueryResultsView rView = (QueryResultsView) evt.getNewValue();
                tabPane.remove(rView);
                rView.close();
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPane = TabbedPaneFactory.createCloseButtonTabbedPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabPane;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        for (int i = tabPane.getTabCount() - 1; i >= 0; i--) {
            try {
                QueryResultsView rView = (QueryResultsView) tabPane.getComponentAt(i);
                rView.close();
                tabPane.remove(rView);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    public void addResultsView(QueryResultsView aView) throws Exception {
        tabPane.add(aView);
        tabPane.setSelectedComponent(aView);
        aView.runQuery();
    }
}
