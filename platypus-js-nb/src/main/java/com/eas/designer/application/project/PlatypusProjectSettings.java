package com.eas.designer.application.project;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.logging.Level;
import org.openide.util.EditableProperties;

/**
 *
 * @author vv
 */
public interface PlatypusProjectSettings {

    public static final String START_JS_FILE_NAME = "start.js"; //NOI18N

    PropertyChangeSupport getChangeSupport();

    /**
     * Gets default application element to run.
     *
     * @return application element name
     */
    String getRunElement();

    /**
     * Sets default application element to run.
     *
     * @param aValue application element name
     * @throws java.lang.Exception
     */
    void setRunElement(String aValue) throws Exception;

    /**
     * Gets sources path within the project.
     *
     * @return Source path subfolder
     */
    String getSourcePath();

    /**
     * Sets sources path within the project.
     *
     * @param aValue Subfolder value
     */
    void setSourcePath(String aValue);

    /**
     * Get the default data source name
     *
     * @return string of the default data source name
     */
    String getDefaultDataSourceName();

    /**
     * Sets the default data source name for a project
     *
     * @param aValue a default data source name
     */
    void setDefaultDatasourceName(String aValue);

    /**
     * Gets the url used with platypus client.
     *
     * @return Url string
     */
    String getPlatypusClientUrl();

    /**
     * Gets the log level for Platypus Client.
     *
     * @return Log level value
     */
    Level getPlatypusClientLogLevel();

    /**
     * Sets a log level for Platypus Client.
     *
     * @param aValue Log level value
     */
    void setPlatypusClientLogLevel(Level aValue);

    /**
     * Checks if security realm is enabled.
     *
     * @return true to enable configure security realm
     */
    boolean getSecurityRealmEnabled();

    /**
     * Sets if security realm is enabled.
     *
     * @param aValue true to enable configure security realm
     */
    void setSecurityRealmEnabled(boolean aValue);

    boolean getAutoApplyWebSettings();

    void setAutoApplyWebSettings(boolean aValue);

    /**
     * Gets the log level for Platypus Server.
     *
     * @return Log level value
     */
    Level getPlatypusServerLogLevel();

    /**
     * Sets a log level for Platypus Server.
     *
     * @param aValue Log level value
     */
    void setPlatypusServerLogLevel(Level aValue);

    /**
     * Gets the log level for servlet container.
     *
     * @return Log level value
     */
    Level getServletContainerLogLevel();

    /**
     * Sets a log level for servlet container.
     *
     * @param aValue Log level value
     */
    void setServletContainerLogLevel(Level aValue);

    /**
     * Gets JPDA debugging port for Platypus Client on local computer on
     * development if null or empty, use default value.
     *
     * @return JPDA debugging port
     */
    int getPlatypusClientDebugPort();

    boolean getBrowserCacheBusting();

    boolean getGlobalAPI();

    /**
     * Gets JMX debugging port for Platypus Application Server on local computer
     * on development if null or empty, use default value.
     *
     * @return JMX debugging port
     */
    int getPlatypusServerDebugPort();

    /**
     * Gets the project's display name.
     *
     * @return title for the project
     */
    String getDisplayName();

    /**
     * Gets application server type to be run.
     *
     * @return AppServerType instance
     */
    AppServerType getApplicationServerType();

    /**
     * Gets client type to be run.
     *
     * @return ClientType instance
     */
    ClientType getClientType();

    /**
     * Gets application's context name.
     *
     * @return The name of the context string
     */
    String getWebApplicationContext();

    /**
     * Gets http port.
     *
     * @return server port
     */
    int getServletContainerPort();

    /**
     * Gets servlet container JPDA port.
     *
     * @return JPDA port of servlet container.
     */
    int getServletContainerDebugPort();

    /**
     * Gets platypus port.
     *
     * @return server port
     */
    int getPlatypusServerPort();

    /**
     * Checks if start local development platypus server on application run.
     *
     * @return true not to start server
     */
    boolean getStartLocalPlatypusServer();

    /**
     * Checks if start local development servlet container on application run.
     *
     * @return true not to start server
     */
    boolean getStartLocalServletContainer();

    /**
     * Saves the project settings.
     *
     * @throws Exception if something goes wrong
     */
    void save() throws Exception;

    /**
     * Sets url used with platyous client.
     *
     * @param aValue Url string
     */
    void setPlatypusClientUrl(String aValue);

    /**
     * Sets JPDA debugging port for Platypus Client on local computer on
     * development.
     *
     * @param aValue JPDA debugging port
     */
    void setPlatypusClientDebugPort(int aValue);

    void setBrowserCacheBusting(boolean aValue);

    void setGlobalAPI(boolean aValue);

    /**
     * Sets JPDA debugging port for platypus server on local computer on
     * development.
     *
     * @param aValue JPDA debugging port
     */
    void setPlatypusServerDebugPort(int aValue);

    /**
     * Sets the project's display name.
     *
     * @param aValue title for the project
     */
    void setDisplayName(String aValue);

    /**
     * Sets flag to start local development platypus server on application run.
     *
     * @param aValue true not to start server
     */
    void setStartLocalPlatypusServer(boolean aValue);

    /**
     * Sets flag to start local development servlet container on application
     * run.
     *
     * @param aValue true not to start server
     */
    void setStartLocalServletContainer(boolean aValue);

    /**
     * Checks if datasources from designer should be placed into
     * project.properties
     *
     * @return true if datasources from designer should be placed into
     * project.properties
     */
    boolean getAcceptDesginerDatasources();

    /**
     * Sets flag to place datasources from designer into project.properties.
     *
     * @param aValue true if datasources from designer should be placed into
     * project.properties
     */
    public void setAcceptDesignerDatasources(boolean aValue);

    /**
     * Sets application server type to be run.
     *
     * @param aValue AppServerType instance
     */
    void setApplicationServerType(AppServerType aValue);

    /**
     * Sets client type to be run.
     *
     * @param aValue ClientType instance
     */
    void setClientType(ClientType aValue);

    /**
     * Sets application's context name.
     *
     * @param aValue The name of the context string
     */
    void setWebApplicationContext(String aValue);

    /**
     * Sets platypus port.
     *
     * @param aValue server port
     */
    void setPlatypusServerPort(int aValue);

    /**
     * Sets http port.
     *
     * @param aValue http port.
     */
    void setServletContainerPort(int aValue);

    /**
     * Sets servel container JPDA port.
     *
     * @param aValue JPDA port.
     */
    void setServletContainerDebugPort(int aValue);

    String getCleanCommand();

    String getBuildCommand();
    
    String getPlatypusServerRunCommand();

    String getServletContainerRunCommand();

    String getPlatypusClientRunCommand();

    void setCleanCommand(String aValue);

    void setBuildCommand(String aValue);
    
    void setPlatypusServerRunCommand(String aValue);

    void setServletContainerRunCommand(String aValue);

    void setPlatypusClientRunCommand(String aValue);

    String getBrowserCustomUrl();

    String getBrowserRunCommand();

    void setBrowserCustomUrl(String aValue);

    void setBrowserRunCommand(String aValue);

    void load() throws IOException;

    public EditableProperties getProjectProperties();

    public EditableProperties getProjectPrivateProperties();

}
