package com.eas.designer.explorer.project.ui;

import com.eas.designer.application.indexer.IndexerQuery;
import com.eas.designer.explorer.FileChooser;
import com.eas.designer.application.project.AppServerType;
import com.eas.designer.application.project.ClientType;
import com.eas.designer.application.project.PlatypusProject;
import com.eas.designer.application.project.PlatypusProjectSettings;
import com.eas.designer.application.utils.DatabaseConnectionRenderer;
import com.eas.designer.application.utils.DatabaseConnections;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author mg
 */
public class ProjectGeneralCustomizer extends javax.swing.JPanel {

    protected final PlatypusProject project;
    protected final FileObject appRoot;
    protected final PlatypusProjectSettings projectSettings;
    private boolean isInit;

    /**
     * Creates new form ProjectRunningCustomizer
     *
     * @param aProject
     */
    public ProjectGeneralCustomizer(PlatypusProject aProject) {
        project = aProject;
        appRoot = aProject.getSrcRoot();
        projectSettings = aProject.getSettings();
        initComponents();
        setupConnectionsModel();
        cbConnections.setRenderer(new DatabaseConnectionRenderer(null));
        cbConnections.setSelectedItem(projectSettings.getDefaultDataSourceName() != null ? DatabaseConnections.lookup(projectSettings.getDefaultDataSourceName()) : null);
        cbConnections.addActionListener((ActionEvent e) -> {
            DatabaseConnection conn = (DatabaseConnection) cbConnections.getSelectedItem();
            projectSettings.setDefaultDatasourceName(conn != null ? conn.getDisplayName() : null);
        });
        cbConnections.addItemListener((ItemEvent e) -> {
            DatabaseConnection conn = (DatabaseConnection) cbConnections.getSelectedItem();
            projectSettings.setDefaultDatasourceName(conn != null ? conn.getDisplayName() : null);
        });
        EventQueue.invokeLater(() -> {
            isInit = true;
            try {
                txtProjectFolder.setText(project.getProjectDirectory().getPath());
                if (projectSettings.getRunElement() != null) {
                    txtRunPath.setText(projectSettings.getRunElement());
                }
                if (projectSettings.getSourcePath() != null) {
                    txtSourcePath.setText(projectSettings.getSourcePath());
                }
                if (projectSettings.getTestPath() != null) {
                    txtTestPath.setText(projectSettings.getTestPath());
                }
                if (projectSettings.getCleanCommand() != null) {
                    txtCleanCommand.setText(projectSettings.getCleanCommand());
                }
                if (projectSettings.getBuildCommand()!= null) {
                    txtBuildCommand.setText(projectSettings.getBuildCommand());
                }
                chGlobalApi.setSelected(projectSettings.getGlobalAPI());
                chAcceptDesignerDatasources.setSelected(projectSettings.getAcceptDesginerDatasources());
                cbClientType.setSelectedItem(projectSettings.getClientType());
                cbAppServerType.setSelectedItem(projectSettings.getApplicationServerType());
                checkRunClientServerConfiguration();
            } finally {
                isInit = false;
            }
        });
    }

    private void setupConnectionsModel() {
        String selectedName = cbConnections.getSelectedItem() != null ? ((DatabaseConnection) cbConnections.getSelectedItem()).getDisplayName() : null;
        cbConnections.setModel(new DefaultComboBoxModel(ConnectionManager.getDefault().getConnections()));
        ((DefaultComboBoxModel) cbConnections.getModel()).insertElementAt(null, 0);
        if (selectedName != null) {
            for (DatabaseConnection c : ConnectionManager.getDefault().getConnections()) {
                if (c.getDisplayName() != null && c.getDisplayName().equals(selectedName)) {
                    cbConnections.setSelectedItem(c);
                    return;
                }
            }
        }
    }

    private void checkRunClientServerConfiguration() {
        lblClientServerMessage.setVisible(isInvalidRunClientServerConfiguration());
    }

    private boolean isInvalidRunClientServerConfiguration() {
        return ClientType.WEB_BROWSER.equals(cbClientType.getSelectedItem()) && !AppServerType.SERVLET_CONTAINER.equals(cbAppServerType.getSelectedItem());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtRunPath = new javax.swing.JTextField();
        lblRunPath = new javax.swing.JLabel();
        btnBrowse = new javax.swing.JButton();
        cbClientType = new javax.swing.JComboBox();
        lblClientType = new javax.swing.JLabel();
        lblServeType = new javax.swing.JLabel();
        cbAppServerType = new javax.swing.JComboBox();
        lblClientServerMessage = new javax.swing.JLabel();
        cbConnections = new javax.swing.JComboBox();
        lblDefDatasource = new javax.swing.JLabel();
        btnAddDatasource = new javax.swing.JButton();
        chGlobalApi = new javax.swing.JCheckBox();
        txtSourcePath = new javax.swing.JTextField();
        lblSourcePath = new javax.swing.JLabel();
        lblCleanCommand = new javax.swing.JLabel();
        txtCleanCommand = new javax.swing.JTextField();
        lblBuildCommand = new javax.swing.JLabel();
        txtBuildCommand = new javax.swing.JTextField();
        chAcceptDesignerDatasources = new javax.swing.JCheckBox();
        lblProjectFolder = new javax.swing.JLabel();
        txtProjectFolder = new javax.swing.JTextField();
        lblTestPath = new javax.swing.JLabel();
        txtTestPath = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        txtRunPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRunPathFocusLost(evt);
            }
        });
        txtRunPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRunPathActionPerformed(evt);
            }
        });

        lblRunPath.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblRunPath.text")); // NOI18N

        btnBrowse.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.btnBrowse.text")); // NOI18N
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        cbClientType.setModel(new javax.swing.DefaultComboBoxModel(ClientType.values()));
        cbClientType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbClientTypeItemStateChanged(evt);
            }
        });

        lblClientType.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblClientType.text")); // NOI18N

        lblServeType.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblServeType.text")); // NOI18N

        cbAppServerType.setModel(new javax.swing.DefaultComboBoxModel(AppServerType.values()));
        cbAppServerType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbAppServerTypeItemStateChanged(evt);
            }
        });
        cbAppServerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAppServerTypeActionPerformed(evt);
            }
        });

        lblClientServerMessage.setForeground(new java.awt.Color(153, 0, 0));
        lblClientServerMessage.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblClientServerMessage.text")); // NOI18N

        lblDefDatasource.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblDefDatasource.text")); // NOI18N

        btnAddDatasource.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.btnAddDatasource.text")); // NOI18N
        btnAddDatasource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDatasourceActionPerformed(evt);
            }
        });

        chGlobalApi.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.chGlobalApi.text")); // NOI18N
        chGlobalApi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chGlobalApiActionPerformed(evt);
            }
        });

        txtSourcePath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSourcePathFocusLost(evt);
            }
        });
        txtSourcePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSourcePathActionPerformed(evt);
            }
        });

        lblSourcePath.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblSourcePath.text")); // NOI18N

        lblCleanCommand.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblCleanCommand.text")); // NOI18N

        txtCleanCommand.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCleanCommandFocusLost(evt);
            }
        });
        txtCleanCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCleanCommandActionPerformed(evt);
            }
        });

        lblBuildCommand.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblBuildCommand.text")); // NOI18N

        txtBuildCommand.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuildCommandFocusLost(evt);
            }
        });
        txtBuildCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuildCommandActionPerformed(evt);
            }
        });

        chAcceptDesignerDatasources.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.chAcceptDesignerDatasources.text")); // NOI18N
        chAcceptDesignerDatasources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chAcceptDesignerDatasourcesActionPerformed(evt);
            }
        });

        lblProjectFolder.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblProjectFolder.text")); // NOI18N

        txtProjectFolder.setEditable(false);

        lblTestPath.setText(org.openide.util.NbBundle.getMessage(ProjectGeneralCustomizer.class, "ProjectGeneralCustomizer.lblTestPath.text")); // NOI18N

        txtTestPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTestPathFocusLost(evt);
            }
        });
        txtTestPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTestPathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCleanCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCleanCommand)
                    .addComponent(lblBuildCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBuildCommand)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chGlobalApi, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chAcceptDesignerDatasources, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 173, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblClientType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblServeType, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(lblDefDatasource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblClientServerMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbClientType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbAppServerType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbConnections, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnAddDatasource, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblTestPath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProjectFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSourcePath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRunPath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProjectFolder)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTestPath, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSourcePath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(txtRunPath))
                                .addGap(18, 18, 18)
                                .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjectFolder)
                    .addComponent(txtProjectFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRunPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRunPath)
                    .addComponent(btnBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSourcePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSourcePath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTestPath)
                    .addComponent(txtTestPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbClientType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClientType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServeType)
                    .addComponent(cbAppServerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbConnections, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDefDatasource)
                    .addComponent(btnAddDatasource))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblClientServerMessage)
                .addGap(18, 18, 18)
                .addComponent(chGlobalApi)
                .addGap(18, 18, 18)
                .addComponent(chAcceptDesignerDatasources)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(lblCleanCommand)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCleanCommand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBuildCommand)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuildCommand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtRunPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRunPathActionPerformed
        try {
            projectSettings.setRunElement(txtRunPath.getText());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_txtRunPathActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        try {
            FileObject selectedFile = null;// TODO Rework app element selector
            Set<String> allowedTypes = new HashSet<>();
            allowedTypes.add("text/javascript");//NOI18N
            FileObject newSelectedFile = FileChooser.selectAppElement(appRoot, selectedFile, allowedTypes);
            if (newSelectedFile != null && newSelectedFile != selectedFile) {
                String appElementName = IndexerQuery.file2AppElementId(newSelectedFile);
                projectSettings.setRunElement(appElementName);
                txtRunPath.setText(appElementName);
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void txtRunPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRunPathFocusLost
        try {
            projectSettings.setRunElement(txtRunPath.getText());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_txtRunPathFocusLost

    private void cbClientTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbClientTypeItemStateChanged
        if (!isInit && evt.getStateChange() == ItemEvent.SELECTED) {
            projectSettings.setClientType((ClientType) cbClientType.getSelectedItem());
            if (!AppServerType.SERVLET_CONTAINER.equals((AppServerType) cbAppServerType.getSelectedItem()) && ClientType.WEB_BROWSER.equals((ClientType) cbClientType.getSelectedItem())) {
                cbAppServerType.setSelectedItem(AppServerType.SERVLET_CONTAINER);
            }
            checkRunClientServerConfiguration();
        }
    }//GEN-LAST:event_cbClientTypeItemStateChanged

    private void cbAppServerTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbAppServerTypeItemStateChanged
        if (!isInit && evt.getStateChange() == ItemEvent.SELECTED) {
            projectSettings.setApplicationServerType((AppServerType) cbAppServerType.getSelectedItem());
            if (!AppServerType.SERVLET_CONTAINER.equals((AppServerType) cbAppServerType.getSelectedItem()) && ClientType.WEB_BROWSER.equals((ClientType) cbClientType.getSelectedItem())) {
                cbClientType.setSelectedItem(ClientType.PLATYPUS_CLIENT);
            }
            checkRunClientServerConfiguration();
        }
    }//GEN-LAST:event_cbAppServerTypeItemStateChanged

    private void btnAddDatasourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDatasourceActionPerformed
        ConnectionManager.getDefault().showAddConnectionDialog(null);
        setupConnectionsModel();
    }//GEN-LAST:event_btnAddDatasourceActionPerformed

    private void chGlobalApiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chGlobalApiActionPerformed
        projectSettings.setGlobalAPI(chGlobalApi.isSelected());
    }//GEN-LAST:event_chGlobalApiActionPerformed

    private void cbAppServerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAppServerTypeActionPerformed
        if (!isInit) {
            projectSettings.setApplicationServerType((AppServerType) cbAppServerType.getSelectedItem());
            if (!AppServerType.SERVLET_CONTAINER.equals((AppServerType) cbAppServerType.getSelectedItem()) && ClientType.WEB_BROWSER.equals((ClientType) cbClientType.getSelectedItem())) {
                cbClientType.setSelectedItem(ClientType.PLATYPUS_CLIENT);
            }
            checkRunClientServerConfiguration();
        }
    }//GEN-LAST:event_cbAppServerTypeActionPerformed

    private void txtSourcePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSourcePathActionPerformed
        projectSettings.setSourcePath(txtSourcePath.getText());
    }//GEN-LAST:event_txtSourcePathActionPerformed

    private void txtSourcePathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSourcePathFocusLost
        projectSettings.setSourcePath(txtSourcePath.getText());
    }//GEN-LAST:event_txtSourcePathFocusLost

    private void txtCleanCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCleanCommandActionPerformed
        projectSettings.setCleanCommand(txtCleanCommand.getText());
    }//GEN-LAST:event_txtCleanCommandActionPerformed

    private void txtCleanCommandFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCleanCommandFocusLost
        projectSettings.setCleanCommand(txtCleanCommand.getText());
    }//GEN-LAST:event_txtCleanCommandFocusLost

    private void txtBuildCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuildCommandActionPerformed
        projectSettings.setBuildCommand(txtBuildCommand.getText());
    }//GEN-LAST:event_txtBuildCommandActionPerformed

    private void txtBuildCommandFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuildCommandFocusLost
        projectSettings.setBuildCommand(txtBuildCommand.getText());
    }//GEN-LAST:event_txtBuildCommandFocusLost

    private void chAcceptDesignerDatasourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chAcceptDesignerDatasourcesActionPerformed
        projectSettings.setAcceptDesignerDatasources(chAcceptDesignerDatasources.isSelected());
    }//GEN-LAST:event_chAcceptDesignerDatasourcesActionPerformed

    private void txtTestPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTestPathFocusLost
        projectSettings.setTestPath(txtTestPath.getText());
    }//GEN-LAST:event_txtTestPathFocusLost

    private void txtTestPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTestPathActionPerformed
        projectSettings.setTestPath(txtTestPath.getText());
    }//GEN-LAST:event_txtTestPathActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDatasource;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JComboBox cbAppServerType;
    private javax.swing.JComboBox cbClientType;
    private javax.swing.JComboBox cbConnections;
    private javax.swing.JCheckBox chAcceptDesignerDatasources;
    private javax.swing.JCheckBox chGlobalApi;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBuildCommand;
    private javax.swing.JLabel lblCleanCommand;
    private javax.swing.JLabel lblClientServerMessage;
    private javax.swing.JLabel lblClientType;
    private javax.swing.JLabel lblDefDatasource;
    private javax.swing.JLabel lblProjectFolder;
    private javax.swing.JLabel lblRunPath;
    private javax.swing.JLabel lblServeType;
    private javax.swing.JLabel lblSourcePath;
    private javax.swing.JLabel lblTestPath;
    private javax.swing.JTextField txtBuildCommand;
    private javax.swing.JTextField txtCleanCommand;
    private javax.swing.JTextField txtProjectFolder;
    private javax.swing.JTextField txtRunPath;
    private javax.swing.JTextField txtSourcePath;
    private javax.swing.JTextField txtTestPath;
    // End of variables declaration//GEN-END:variables
}
