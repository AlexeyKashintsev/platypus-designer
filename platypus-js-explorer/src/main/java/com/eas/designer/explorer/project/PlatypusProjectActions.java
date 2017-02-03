package com.eas.designer.explorer.project;

import com.eas.client.cache.PlatypusFiles;
import com.eas.client.resourcepool.DatasourcesArgsConsumer;
import com.eas.designer.application.PlatypusUtils;
import com.eas.designer.application.project.ClientType;
import com.eas.designer.application.project.PlatypusProject;
import com.eas.designer.application.project.PlatypusProjectSettings;
import com.eas.designer.explorer.j2ee.dd.AppListener;
import com.eas.designer.explorer.j2ee.dd.AuthConstraint;
import com.eas.designer.explorer.j2ee.dd.ContextParam;
import com.eas.designer.explorer.j2ee.dd.FormLoginConfig;
import com.eas.designer.explorer.j2ee.dd.LoginConfig;
import com.eas.designer.explorer.j2ee.dd.MultipartConfig;
import com.eas.designer.explorer.j2ee.dd.ResourceRef;
import com.eas.designer.explorer.j2ee.dd.SecurityConstraint;
import com.eas.designer.explorer.j2ee.dd.SecurityRole;
import com.eas.designer.explorer.j2ee.dd.Servlet;
import com.eas.designer.explorer.j2ee.dd.ServletMapping;
import com.eas.designer.explorer.j2ee.dd.WebApplication;
import com.eas.designer.explorer.j2ee.dd.WebResourceCollection;
import com.eas.designer.explorer.j2ee.dd.WelcomeFile;
import com.eas.server.httpservlet.PlatypusHttpServlet;
import com.eas.server.httpservlet.PlatypusServerConfig;
import com.eas.server.httpservlet.PlatypusSessionsSynchronizer;
import com.eas.util.FileUtils;
import com.eas.util.StringUtils;
import com.eas.xml.dom.XmlDom2String;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.SaveAllAction;
import org.openide.filesystems.FileUtil;
import org.openide.util.Utilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.SystemAction;
import org.openide.windows.InputOutput;
import org.openide.filesystems.FileObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.sql.DataSource;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.openide.util.EditableProperties;
import org.netbeans.api.extexecution.base.ProcessBuilder;

/**
 *
 * @author mg
 */
public class PlatypusProjectActions implements ActionProvider {

    public static final String COMMAND_CONNECT = "connect-to-db"; // NOI18N
    public static final String COMMAND_DISCONNECT = "disconnect-from-db"; // NOI18N
    //private static final String LOCALHOST_NAME = "localhost"; //NOI18N
    /**
     * Some routine global actions for which we can supply a display name. These
     * are IDE-specific.
     */
    private static final Set<String> PROJECT_ACTIONS = new HashSet<>(Arrays.asList(
            COMMAND_CLEAN,
            COMMAND_BUILD,
            COMMAND_REBUILD,
            COMMAND_RUN,
            COMMAND_DELETE,
            COMMAND_COPY,
            COMMAND_MOVE,
            COMMAND_RENAME,
            COMMAND_CONNECT,
            COMMAND_DISCONNECT));

    protected final PlatypusProject project;
    protected final FileObject projectDir;

    public PlatypusProjectActions(PlatypusProject aProject) {
        super();
        project = aProject;
        projectDir = project.getProjectDirectory();
    }

    @Override
    public String[] getSupportedActions() {
        return PROJECT_ACTIONS.toArray(new String[]{});
    }

    @Override
    public void invokeAction(String actionCommand, Lookup aLookup) throws IllegalArgumentException {
        try {
            switch (actionCommand) {
                case COMMAND_DELETE:
                    DefaultProjectOperations.performDefaultDeleteOperation(project);
                    break;
                case COMMAND_COPY:
                    DefaultProjectOperations.performDefaultCopyOperation(project);
                    break;
                case COMMAND_RENAME:
                    DefaultProjectOperations.performDefaultRenameOperation(project, null);
                    break;
                case COMMAND_MOVE:
                    DefaultProjectOperations.performDefaultMoveOperation(project);
                    break;
                case COMMAND_CLEAN:
                    clean();
                    break;
                case COMMAND_BUILD:
                    build();
                    break;
                case COMMAND_REBUILD:
                    rebuild();
                    break;
                case COMMAND_RUN:
                    run();
                    break;
                case COMMAND_CONNECT:
                    project.startConnecting2db(project.getSettings().getDefaultDataSourceName());
                    break;
                case COMMAND_DISCONNECT:
                    project.disconnectFormDb(project.getSettings().getDefaultDataSourceName());
                    break;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public boolean isActionEnabled(String command, Lookup aLookup) throws IllegalArgumentException {
        if (COMMAND_DISCONNECT.equals(command)) {
            return project.isDbConnected(project.getSettings().getDefaultDataSourceName());
        } else if (COMMAND_CONNECT.equals(command)) {
            return !project.isDbConnected(project.getSettings().getDefaultDataSourceName());
        } else if (COMMAND_CLEAN.equals(command)) {
            PlatypusProjectSettings pps = project.getSettings();
            return pps.getCleanCommand() != null && !pps.getCleanCommand().isEmpty();
        } else if (COMMAND_BUILD.equals(command)) {
            PlatypusProjectSettings pps = project.getSettings();
            return pps.getBuildCommand() != null && !pps.getBuildCommand().isEmpty();
        } else if (COMMAND_REBUILD.equals(command)) {
            PlatypusProjectSettings pps = project.getSettings();
            return pps.getCleanCommand() != null && !pps.getCleanCommand().isEmpty()
                    && pps.getBuildCommand() != null && !pps.getBuildCommand().isEmpty();
        } else if (PROJECT_ACTIONS.contains(command)) {
            return true;
        }
        return false;
    }

    private void clean() {
        command(project.getSettings().getCleanCommand(), project.getDisplayName() + " - " + NbBundle.getMessage(PlatypusProjectActions.class, "LBL_CleanAction_Name"), false, null);
    }

    private void build() {
        command(project.getSettings().getBuildCommand(), project.getDisplayName() + " - " + NbBundle.getMessage(PlatypusProjectActions.class, "LBL_BuildAction_Name"), false, null);
    }

    private void rebuild() {
        command(project.getSettings().getCleanCommand(), project.getDisplayName() + " - " + NbBundle.getMessage(PlatypusProjectActions.class, "LBL_CleanAction_Name"), false, () -> {
            command(project.getSettings().getBuildCommand(), project.getDisplayName() + " - " + NbBundle.getMessage(PlatypusProjectActions.class, "LBL_BuildAction_Name"), false, null);
        });
    }

    private void run() throws Exception {
        String runAppElement = project.getSettings().getRunElement();
        if (runAppElement == null || runAppElement.isEmpty()) {
            final SelectAppElementPanel panel = new SelectAppElementPanel(project);
            DialogDescriptor dd = new DialogDescriptor(panel, NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Run_Element_Dialog"));//NOI18N
            if (DialogDescriptor.OK_OPTION.equals(DialogDisplayer.getDefault().notify(dd))) {
                runAppElement = panel.getAppElementId();
                if (panel.isSaveAsDefault()) {
                    project.getSettings().setRunElement(runAppElement);
                    project.getSettings().save();
                }
            } else {
                return;
            }
        }
        start(runAppElement);
    }

    public void start(String aModuleName) throws Exception {
        if (aModuleName != null && !aModuleName.isEmpty()) {
            saveAll();
            preapreStartJs(aModuleName);
            PlatypusProjectSettings pps = project.getSettings();
            if (pps.getAcceptDesginerDatasources()) {
                prepareDatasources();
            }
            InputOutput projectIo = project.getOutputWindowIO();
            projectIo.getOut().println(NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Application_Starting"));
            switch (pps.getApplicationServerType()) {
                case SERVLET_CONTAINER:
                    boolean startServletContainer = pps.getStartLocalServletContainer();
                    if (startServletContainer) {
                        prepareWebApplication();
                        command(pps.getServletContainerRunCommand(), project.getDisplayName() + " - " + NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Starting_Servlet_Container"), true, null);
                        /*
                        if (aDebug) {
                            attachDebuggerTo(pps.getServletContainerDebugPort());
                            projectIo.getOut().println(NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Servlet_Container_Debug_Activated"));//NOI18N
                        }
                         */
                    }
                    break;
                case PLATYPUS_SERVER:
                    boolean startPlatypusServer = pps.getStartLocalPlatypusServer();
                    if (startPlatypusServer) {
                        command(project.getSettings().getPlatypusServerRunCommand(), project.getDisplayName() + " - " + NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Starting_Platypus_Server"), true, null);
                        /*
                        if (aDebug) {
                            attachDebuggerTo(pps.getPlatypusServerDebugPort());
                            projectIo.getOut().println(NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Server_Debug_Activated"));//NOI18N
                        }
                         */
                    }
                    break;
                default:
                    break;
            }
            if (ClientType.PLATYPUS_CLIENT.equals(project.getSettings().getClientType())) {
                command(project.getSettings().getPlatypusClientRunCommand(), project.getDisplayName() + " - " + NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Starting_Platypus_Client"), true, null);
                /*
                if (aDebug) {
                    attachDebuggerTo(pps.getPlatypusClientDebugPort());
                    projectIo.getOut().println(NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Client_Debug_Activated"));//NOI18N
                }
                 */
            } else if (ClientType.WEB_BROWSER.equals(pps.getClientType())) {
                command(project.getSettings().getBrowserRunCommand(), project.getDisplayName() + " - " + NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Starting_Web_Browser"), true, null);
            }
        } else {
            throw new IllegalStateException(NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Start_App_Element_Not_Set"));
        }
    }

    /*
    private void attachDebuggerTo(int aPort) throws MissingResourceException {
        DebuggerEngine[] startedEngines = DebuggerManager.getDebuggerManager().startDebugging(DebuggerInfo.create(AttachingDICookie.ID, new Object[]{AttachingDICookie.create(LOCALHOST_NAME, aPort)}));
        DebuggerEngine justStartedEngine = startedEngines[0];
        DebuggerManager.getDebuggerManager().addDebuggerListener(new DebuggerManagerAdapter() {

            @Override
            public void engineRemoved(DebuggerEngine engine) {
                if (engine == justStartedEngine) {
                    DebuggerManager.getDebuggerManager().removeDebuggerListener(this);
                }
            }

        });
    }
     */
    private void preapreStartJs(String aModuleName) throws IOException, Exception {
        FileObject appSrcDir = project.getSrcRoot();
        FileObject startJs = appSrcDir.getFileObject(PlatypusProjectSettings.START_JS_FILE_NAME);
        if (startJs == null) {
            startJs = appSrcDir.createData(PlatypusProjectSettings.START_JS_FILE_NAME);
        }
        PlatypusProjectSettings pps = project.getSettings();
        File startModule = project.getIndexer().nameToFile(aModuleName);
        if (startModule != null) {
            String requireCallabckArg = moduleIdToVarName(aModuleName);
            FileObject layoutBrother = FileUtil.findBrother(FileUtil.toFileObject(startModule), PlatypusFiles.FORM_EXTENSION);
            String startMethod = layoutBrother != null ? "show" : "execute";
            String starupScript = String.format(PlatypusProjectSettingsImpl.START_JS_FILE_TEMPLATE, pps.getGlobalAPI() ? "facade" : "environment", pps.getBrowserCacheBusting() ? "" : "//", pps.getGlobalAPI() ? "" : "//", aModuleName, requireCallabckArg, "        var m = new " + requireCallabckArg + "();\n", "        m." + startMethod + "();\n", aModuleName);
            FileUtils.writeString(FileUtil.toFile(startJs), starupScript, PlatypusUtils.COMMON_ENCODING_NAME);
        } else if (aModuleName.toLowerCase().endsWith(PlatypusFiles.JAVASCRIPT_FILE_END)) {
            String moduleId = aModuleName.substring(0, aModuleName.length() - PlatypusFiles.JAVASCRIPT_FILE_END.length());
            String requireCallabckArg = moduleIdToVarName(moduleId);
            String starupScript = String.format(PlatypusProjectSettingsImpl.START_JS_FILE_TEMPLATE, pps.getGlobalAPI() ? "facade" : "environment", pps.getBrowserCacheBusting() ? "" : "//", pps.getGlobalAPI() ? "" : "//", aModuleName, requireCallabckArg, "", "    //...\n", aModuleName);
            FileUtils.writeString(FileUtil.toFile(startJs), starupScript, PlatypusUtils.COMMON_ENCODING_NAME);
        }
    }

    private static boolean isConnectionValid(DatabaseConnection connection) {
        return connection.getDisplayName() != null && !connection.getDisplayName().isEmpty() && connection.getDisplayName().matches("[a-zA-Z_][a-zA-Z0-9_]*") //NOI18N
                && connection.getDatabaseURL() != null && !connection.getDatabaseURL().isEmpty()
                && connection.getUser() != null && !connection.getUser().isEmpty();
    }

    /**
     * Iterates through all datasources, registered in the designer and applies
     * them as nested properties of form: datasources.<name>.user=...
     * datasources.<name>.password=... datasources.<name>.url=... ...
     */
    private void prepareDatasources() throws Exception {
        PlatypusProjectSettings pps = project.getSettings();
        EditableProperties projectProperties = pps.getProjectProperties();
        projectProperties.remove(PlatypusProjectSettings.DEFAULT_DATA_SOURCE_ELEMENT_KEY);
        if(pps.getDefaultDataSourceName() != null && !pps.getDefaultDataSourceName().isEmpty()){
            projectProperties.setProperty(PlatypusProjectSettings.DEFAULT_DATA_SOURCE_ELEMENT_KEY, pps.getDefaultDataSourceName());
        }
        // Let's clear old datasources
        projectProperties.entrySet().removeIf(e -> e.getKey().startsWith(DatasourcesArgsConsumer.DB_RESOURCE_CONF_PARAM + "."));
        // Let's add current datasources
        DatabaseConnection[] dataSources = ConnectionManager.getDefault().getConnections();
        for (DatabaseConnection connection : dataSources) {
            if (isConnectionValid(connection)) {
                projectProperties.setProperty(String.join(".", new String[]{DatasourcesArgsConsumer.DB_RESOURCE_CONF_PARAM, connection.getDisplayName(), DatasourcesArgsConsumer.DB_URL_CONF_PARAM}), connection.getDatabaseURL());
                projectProperties.setProperty(String.join(".", new String[]{DatasourcesArgsConsumer.DB_RESOURCE_CONF_PARAM, connection.getDisplayName(), DatasourcesArgsConsumer.DB_USERNAME_CONF_PARAM}), connection.getUser());
                if (connection.getPassword() != null && !connection.getPassword().isEmpty()) {
                    projectProperties.setProperty(String.join(".", new String[]{DatasourcesArgsConsumer.DB_RESOURCE_CONF_PARAM, connection.getDisplayName(), DatasourcesArgsConsumer.DB_PASSWORD_CONF_PARAM}), connection.getPassword());
                }
                if (connection.getSchema() != null && !connection.getSchema().isEmpty()) {
                    projectProperties.setProperty(String.join(".", new String[]{DatasourcesArgsConsumer.DB_RESOURCE_CONF_PARAM, connection.getDisplayName(), DatasourcesArgsConsumer.DB_SCHEMA_CONF_PARAM}), connection.getSchema());
                }
                projectProperties.setProperty(String.join(".", new String[]{DatasourcesArgsConsumer.DB_RESOURCE_CONF_PARAM, connection.getDisplayName(), "jdbcDriverClass"}), connection.getDriverClass());
            } else {
                project.getOutputWindowIO().getErr().println(NbBundle.getMessage(PlatypusProjectActions.class, "MSG_Invalid_Database", connection.getDisplayName()));
            }
        }
        try (OutputStream os = pps.getProjectSettingsFileObject().getOutputStream()) {
            projectProperties.store(os);
        }
    }

    private void prepareWebApplication() throws Exception {
        prepareGeneralPages();
        FileObject webInfDir = projectDir.getFileObject(PlatypusProject.WEB_INF_DIRECTORY);
        FileObject webXml = webInfDir.getFileObject(WEB_XML_FILE_NAME);
        if (webXml == null || project.getSettings().getAutoApplyWebSettings()) {
            WebApplication wa = new WebApplication();
            wa.addAppListener(new AppListener(PlatypusSessionsSynchronizer.class.getName()));
            configureParams(wa);
            configureServlet(wa);
            configureDatasources(wa);
            if (project.getSettings().getSecurityRealmEnabled()) {
                configureSecurity(wa);
            }
            if (webXml == null) {
                webXml = webInfDir.createData(WEB_XML_FILE_NAME);
            }
            FileUtils.writeString(FileUtil.toFile(webXml), XmlDom2String.transform(wa.toDocument()), PlatypusUtils.COMMON_ENCODING_NAME);
        }
    }

    private static final String PLATYPUS_SERVLET_URL_PATTERN = "/application/*"; //NOI18N
    private static final String J2EE_RESOURCES_PACKAGE = "/com/eas/designer/explorer/j2ee/"; //NOI18N
    private static final String START_PAGE_FILE_NAME = "application-start.html"; //NOI18N
    private static final String LOGIN_PAGE_FILE_NAME = "login.html"; //NOI18N
    private static final String LOGIN_FAIL_PAGE_FILE_NAME = "login-failed.html"; //NOI18N
    private static final String WEB_XML_FILE_NAME = "web.xml"; //NOI18N
    private static final String PLATYPUS_SERVLET_NAME = "PlatypusServlet"; //NOI18N
    private static final String PUBLIC_DIRECTORY_NAME = "pub"; //NOI18N
    private static final String CONTAIER_RESOURCE_SECURITY_TYPE = "Container"; //NOI18N
    private static final String PLATYPUS_WEB_RESOURCE_NAME = "platypus"; //NOI18N
    private static final String ANY_SIGNED_USER_ROLE = "*"; //NOI18N
    private static final String FORM_AUTH_METHOD = "FORM"; //NOI18N
    private static final String BASIC_AUTH_METHOD = "BASIC"; //NOI18N
    private static final long MULTIPART_MAX_FILE_SIZE = 2097152;
    private static final long MULTIPART_MAX_REQUEST_SIZE = 2165824;
    private static final long MULTIPART_MAX_FILE_THRESHOLD = 1048576;

    private void prepareGeneralPages() throws IOException {
        ensureResource(START_PAGE_FILE_NAME);
        ensureResource(LOGIN_PAGE_FILE_NAME);
        ensureResource(LOGIN_FAIL_PAGE_FILE_NAME);
    }

    private void ensureResource(String filePath) throws IOException {
        FileObject fo = projectDir.getFileObject(filePath);
        if (fo == null) {
            fo = projectDir.createData(filePath);
            try (InputStream is = PlatypusProjectActions.class.getResourceAsStream(J2EE_RESOURCES_PACKAGE + filePath);
                    OutputStream os = fo.getOutputStream()) {
                FileUtil.copy(is, os);
            }
        }
    }

    private void configureParams(WebApplication wa) throws Exception {
        wa.addInitParam(new ContextParam(PlatypusServerConfig.DEF_DATASOURCE_CONF_PARAM, project.getSettings().getDefaultDataSourceName()));
        wa.addInitParam(new ContextParam(PlatypusServerConfig.APPELEMENT_CONF_PARAM, PlatypusProjectSettings.START_JS_FILE_NAME));
        if (project.getSettings().getGlobalAPI()) {
            wa.addInitParam(new ContextParam(PlatypusServerConfig.GLOBAL_API_CONF_PARAM, "" + true));
        }
        if (project.getSettings().getSourcePath() != null && !project.getSettings().getSourcePath().isEmpty()) {
            wa.addInitParam(new ContextParam(PlatypusServerConfig.SOURCE_PATH_CONF_PARAM, project.getSettings().getSourcePath()));
        }
    }

    private void configureServlet(WebApplication wa) throws IOException {
        FileObject publicDir = createFolderIfNotExists(projectDir, PUBLIC_DIRECTORY_NAME);
        Servlet platypusServlet = new Servlet(PLATYPUS_SERVLET_NAME, PlatypusHttpServlet.class.getName());
        MultipartConfig multiPartConfig = new MultipartConfig();
        multiPartConfig.setLocation(publicDir.getPath());
        multiPartConfig.setMaxFileSize(Long.toString(MULTIPART_MAX_FILE_SIZE));
        multiPartConfig.setMaxRequestSize(Long.toString(MULTIPART_MAX_REQUEST_SIZE));
        multiPartConfig.setFileSizeThreshold(Long.toString(MULTIPART_MAX_FILE_THRESHOLD));
        platypusServlet.setMultipartConfig(multiPartConfig);
        wa.addServlet(platypusServlet);
        wa.addServletMapping(new ServletMapping(PLATYPUS_SERVLET_NAME, PLATYPUS_SERVLET_URL_PATTERN));
        wa.addWelcomeFile(new WelcomeFile(START_PAGE_FILE_NAME));
    }

    private void configureDatasources(WebApplication wa) {
        for (DatabaseConnection conn : ConnectionManager.getDefault().getConnections()) {
            ResourceRef resourceRef = new ResourceRef(conn.getDisplayName(), DataSource.class.getName(), CONTAIER_RESOURCE_SECURITY_TYPE);
            resourceRef.setDescription(conn.getName()); //NOI18N
            wa.addResourceRef(resourceRef);
        }
    }

    private void configureSecurity(WebApplication aWebApplication) {
        // Protect all web application resources
        SecurityConstraint scWholeResources = new SecurityConstraint();
        WebResourceCollection wrcWholeResources = new WebResourceCollection(PLATYPUS_WEB_RESOURCE_NAME);
        wrcWholeResources.setUrlPattern("/*"); //NOI18N
        scWholeResources.addWebResourceCollection(wrcWholeResources);
        scWholeResources.setAuthConstraint(new AuthConstraint(ANY_SIGNED_USER_ROLE));
        aWebApplication.addSecurityConstraint(scWholeResources);
        // Unprotect login page
        SecurityConstraint scLogin = new SecurityConstraint();
        WebResourceCollection wrcLogin = new WebResourceCollection(PLATYPUS_WEB_RESOURCE_NAME + "-login");
        wrcLogin.setUrlPattern("/" + LOGIN_PAGE_FILE_NAME); //NOI18N
        scLogin.addWebResourceCollection(wrcLogin);
        aWebApplication.addSecurityConstraint(scLogin);
        // Unprotect login failed page
        SecurityConstraint scLoginFailed = new SecurityConstraint();
        WebResourceCollection wrcLoginFailed = new WebResourceCollection(PLATYPUS_WEB_RESOURCE_NAME + "-login-failed");
        wrcLoginFailed.setUrlPattern("/" + LOGIN_FAIL_PAGE_FILE_NAME); //NOI18N
        scLoginFailed.addWebResourceCollection(wrcLoginFailed);
        aWebApplication.addSecurityConstraint(scLoginFailed);
        //
        LoginConfig loginConfig = new LoginConfig();
        loginConfig.setAuthMethod(FORM_AUTH_METHOD);
        loginConfig.setFormLoginConfig(new FormLoginConfig("/" + LOGIN_PAGE_FILE_NAME, "/" + LOGIN_FAIL_PAGE_FILE_NAME));//NOI18N
        //
        aWebApplication.addSecurityRole(new SecurityRole(ANY_SIGNED_USER_ROLE));
        aWebApplication.setLoginConfig(loginConfig);
    }

    private static FileObject createFolderIfNotExists(FileObject dir, String name) throws IOException {
        FileObject fo = dir.getFileObject(name);
        if (fo == null) {
            fo = dir.createFolder(name);
        }
        return fo;
    }

    protected static void saveAll() {
        SaveAllAction action = SystemAction.get(SaveAllAction.class);
        if (action != null) {
            action.performAction();
        }
    }

    protected static String moduleIdToVarName(String moduleId) {
        String requireCallabckArg = moduleId;
        int lastFileSepIndex = requireCallabckArg.lastIndexOf(File.separator);
        if (lastFileSepIndex != -1) {
            requireCallabckArg = requireCallabckArg.substring(lastFileSepIndex + 1);
        }
        lastFileSepIndex = requireCallabckArg.lastIndexOf("/");
        if (lastFileSepIndex != -1) {
            requireCallabckArg = requireCallabckArg.substring(lastFileSepIndex + 1);
        }
        return StringUtils.replaceUnsupportedSymbols(requireCallabckArg);
    }

    static List<String> parseArgs(String aCommandLine) {
        List<String> args = new ArrayList<>();
        if (aCommandLine != null && !aCommandLine.isEmpty()) {
            Pattern escapedPattern = Pattern.compile("\"(.*)\"");
            int start = 0;
            Matcher escaped = escapedPattern.matcher(aCommandLine);
            while (escaped.find()) {
                String prefix = aCommandLine.substring(start, escaped.start());
                if (!prefix.isEmpty()) {
                    args.addAll(Arrays.asList(prefix.split(" ")));
                }
                args.add(escaped.group(1));
                start = escaped.end() + 1;
            }
            String suffix = start <= aCommandLine.length() ? aCommandLine.substring(start, aCommandLine.length()) : "";
            if (!suffix.isEmpty()) {
                args.addAll(Arrays.asList(suffix.split(" ")));
            }
        }
        return args;
    }

    static List<String> backslashArgs(List<String> args, FileObject aWorkingDirectoryFo) {
        List<String> backslashedArgs = new ArrayList<>();
        if (Utilities.isWindows()) {
            for (int i = 0; i < args.size(); i++) {
                String arg = args.get(i);
                if (arg.contains("/")) {
                    String backslashed = arg.replaceAll("/", "\\\\");
                    String aWorkingDirectory = FileUtil.toFile(aWorkingDirectoryFo).getAbsolutePath();
                    if (new File(aWorkingDirectory + File.separator + backslashed).exists()
                            || new File(aWorkingDirectory + File.separator + backslashed + ".exe").exists()
                            || new File(aWorkingDirectory + File.separator + backslashed + ".cmd").exists()
                            || new File(aWorkingDirectory + File.separator + backslashed + ".bat").exists()) {
                        backslashedArgs.add(backslashed);
                    } else {
                        backslashedArgs.add(arg);
                    }
                } else {
                    backslashedArgs.add(arg);
                }
            }
        } else {
            backslashedArgs.addAll(args);
        }
        return backslashedArgs;
    }

    private void command(String aCommand, String aDescription, boolean separate, Runnable onComplete) {
        if (aCommand != null && !aCommand.isEmpty()) {
            InputOutput projectIo = project.getOutputWindowIO();
            try {
                projectIo.getOut().println(aCommand);//NOI18N
                ProcessBuilder pb = ProcessBuilder.getLocal();
                String commandsProcessorPrefix = lookupCommandsProcessor(pb);
                List<String> arguments = backslashArgs(parseArgs(aCommand), project.getProjectDirectory());
                if (commandsProcessorPrefix != null && !commandsProcessorPrefix.isEmpty()) {
                    arguments.add(0, commandsProcessorPrefix);
                }
                pb.setArguments(arguments);
                pb.setWorkingDirectory(project.getProjectDirectory().getPath());
                ExecutionDescriptor descriptor = (separate
                        ? new ExecutionDescriptor()
                                .frontWindow(true)
                                .controllable(true)
                                .showProgress(true)
                                .showSuspended(true)
                        : new ExecutionDescriptor()
                                .frontWindow(true)
                                .inputOutput(projectIo)
                                .noReset(true)
                                .showProgress(true)).postExecution(() -> {
                    if (onComplete != null) {
                        if (!separate) {
                            projectIo.getOut().println();
                        }
                        onComplete.run();
                    }
                });
                ExecutionService commandExecution = ExecutionService.newService(pb, descriptor, aDescription);
                commandExecution.run();
            } catch (IOException ex) {
                projectIo.getErr().println(ex.getMessage());
            }
        }
    }

    private String lookupCommandsProcessor(ProcessBuilder pb) throws IOException {
        if (Utilities.isWindows()) {
            pb.setExecutable("cmd.exe");
            return "/C";
        } else {
            Process env = new java.lang.ProcessBuilder().command("/usr/bin/env", "sh").start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(env.getInputStream()))) {
                String commandsProcessorPrefix = reader.readLine();
                pb.setExecutable(commandsProcessorPrefix.replaceAll("[\r\n]", ""));
            }
            return null;
        }
    }
}
