package com.eas.designer.application.query;

import java.beans.PropertyChangeListener;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.api.sql.execute.SQLExecution;

/**
 *
 * @author mgainullin
 */
public class PlatypusSqlExecution implements SQLExecution {

    private DatabaseConnection conn;
    
    @Override
    public DatabaseConnection getDatabaseConnection() {
        return conn;
    }

    @Override
    public void setDatabaseConnection(DatabaseConnection dc) {
        conn = dc;
    }

    @Override
    public void execute() {
    }

    @Override
    public void executeSelection() {
    }

    @Override
    public boolean isExecuting() {
        return false;
    }

    @Override
    public boolean isSelection() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pl) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pl) {
    }

    @Override
    public void showHistory() {
    }
    
}
