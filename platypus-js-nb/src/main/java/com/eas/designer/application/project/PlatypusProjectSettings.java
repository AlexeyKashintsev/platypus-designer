package com.eas.designer.application.project;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.logging.Level;
import org.openide.filesystems.FileObject;
import org.openide.util.EditableProperties;

/**
 *
 * @author vv
 */
public interface PlatypusProjectSettings {

    public static final int DEFAULT_PLATYPUS_SERVER_PORT         = 7500;
    public static final int DEFAULT_SERVLET_CONTAINER_PORT       = 8085;
    public static final int DEFAULT_PLATYPUS_CLIENT_DEBUG_PORT   = 5001;
    public static final int DEFAULT_PLATYPUS_SERVER_DEBUG_PORT   = 5004;
    public static final int DEFAULT_SERVLET_CONTAINER_DEBUG_PORT = 5007;
    public static final Level DEFAULT_LOG_LEVEL                  = Level.INFO;
    public static final String DEFAULT_APP_FOLDER                = "app"; //NOI18N
    public static final String PROJECT_COMMANDS_FILE             = ".platypus"; //NOI18N
    public static final String PROJECT_PRIVATE_SETTINGS_FILE     = "private.properties"; //NOI18N
    public static final String PROJECT_SETTINGS_FILE             = "project.properties"; //NOI18N
    public static final String PROJECT_DISPLAY_NAME_KEY          = "project.displayName"; //NOI18N
    public static final String CLEAN_COMMAND_KEY                 = "project.cleanCommand"; //NOI18N
    public static final String BUILD_COMMAND_KEY                 = "project.buildCommand"; //NOI18N
    public static final String ACCEPT_DESIGNER_DATASOURCES_KEY   = "project.acceptNetBeansDatasources"; //NOI18N
    public static final String DEFAULT_DATA_SOURCE_ELEMENT_KEY   = "project.generalDataSource"; //NOI18N
    public static final String RUN_ELEMENT_KEY                   = "run.module"; //NOI18N
    public static final String GLOBAL_API_KEY                    = "run.globalAPI";//NOI18N
    public static final String CLIENT_TYPE_KEY                   = "run.clientType"; //NOI18N
    public static final String SERVER_TYPE_KEY                   = "run.serverType"; //NOI18N
    public static final String SOURCE_PATH_KEY                   = "run.sourcePath"; //NOI18N
    public static final String HTTP_PORT_KEY                     = "http.port";//NOI18N
    public static final String PLATYPUS_PORT_KEY                 = "platypus.port";//NOI18N
    public static final String WEB_APPLICATION_CONTEXT_KEY       = "webApplication.context";//NOI18N
    public static final String AUTO_APPLY_WEB_XML_KEY            = "webApplication.autoApplyWebXml"; //NOI18N
    public static final String ENABLE_SECURITY_REALM_KEY         = "webApplication.enableSecurityRealm";//NOI18N
    public static final String BROWSER_CUSTOM_URL_KEY            = "browser.customUrl";//NOI18N
    public static final String BROWSER_CACHE_BUSTING_KEY         = "browser.cacheBusting";//NOI18N
    public static final String BROWSER_RUN_COMMAND_KEY           = "browser.runCommand"; //NOI18N
    public static final String PLATYPUS_CLIENT_URL_KEY           = "platypusClient.customUrl";//NOI18N
    public static final String PLATYPUS_CLIENT_LOG_LEVEL_KEY     = "platypusClient.logLevel"; //NOI18N
    public static final String PLATYPUS_CLIENT_DEBUG_PORT_KEY    = "platypusClient.debugPort"; //NOI18N
    public static final String PLATYPUS_CLIENT_RUN_COMMAND_KEY   = "platypusClient.runCommand"; //NOI18N
    public static final String PLATYPUS_SERVER_LOG_LEVEL_KEY     = "platypusServer.logLevel"; //NOI18N
    public static final String PLATYPUS_SERVER_DEBUG_PORT_KEY    = "platypusServer.debugPort"; //NOI18N
    public static final String START_LOCAL_PLATYPUS_SERVER_KEY   = "platypusServer.startLocal"; //NOI18N
    public static final String PLATYPUS_SERVER_RUN_COMMAND_KEY   = "platypusServer.runCommand"; //NOI18N
    public static final String SERVLET_CONTAINER_LOG_LEVEL_KEY   = "servletContainer.logLevel"; //NOI18N
    public static final String SERVLET_CONTAINER_DEBUG_PORT_KEY  = "servletContainer.debugPort"; //NOI18N
    public static final String START_LOCAL_SERVLET_CONTAINER_KEY = "servletContainer.startLocal"; //NOI18N
    public static final String SERVLET_CONTAINER_RUN_COMMAND_KEY = "servletContainer.runCommand"; //NOI18N
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
    
    public FileObject getProjectSettingsFileObject();

    public EditableProperties getProjectPrivateProperties();

    public FileObject getProjectPrivateSettingsFileObject();
}
