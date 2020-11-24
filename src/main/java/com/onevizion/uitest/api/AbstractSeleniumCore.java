package com.onevizion.uitest.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.internal.OkHttpClient;
import org.slf4j.profiler.Profiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;

import com.onevizion.uitest.api.annotation.SeleniumBug;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.CloneButton;
import com.onevizion.uitest.api.helper.CompAuditLog;
import com.onevizion.uitest.api.helper.DropDown;
import com.onevizion.uitest.api.helper.DualListbox;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementJs;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Favorites;
import com.onevizion.uitest.api.helper.FieldHistory;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Help;
import com.onevizion.uitest.api.helper.HtmlSelect;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Listbox;
import com.onevizion.uitest.api.helper.Login;
import com.onevizion.uitest.api.helper.Logoff;
import com.onevizion.uitest.api.helper.Nav;
import com.onevizion.uitest.api.helper.Privilegies;
import com.onevizion.uitest.api.helper.Selector;
import com.onevizion.uitest.api.helper.Qs;
import com.onevizion.uitest.api.helper.RelationSelector;
import com.onevizion.uitest.api.helper.SharePageLink;
import com.onevizion.uitest.api.helper.ShowSql;
import com.onevizion.uitest.api.helper.Tb;
import com.onevizion.uitest.api.helper.UsersSettings;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.api.v3.ApiV3Endpoint;
import com.onevizion.uitest.api.helper.api.v3.ApiV3Parameter;
import com.onevizion.uitest.api.helper.api.v3.ApiV3Resource;
import com.onevizion.uitest.api.helper.bpl.export.BplExport;
import com.onevizion.uitest.api.helper.chat.Chat;
import com.onevizion.uitest.api.helper.chat.ListboxChatMessage;
import com.onevizion.uitest.api.helper.chat.ListboxChatSearchMessage;
import com.onevizion.uitest.api.helper.chat.ListboxChatSubscriber;
import com.onevizion.uitest.api.helper.clipboard.Clipboard;
import com.onevizion.uitest.api.helper.colorpicker.ColorPicker;
import com.onevizion.uitest.api.helper.comment.Comment;
import com.onevizion.uitest.api.helper.comppack.ComponentPackage;
import com.onevizion.uitest.api.helper.configfield.ConfigField;
import com.onevizion.uitest.api.helper.dashboard.Dashboard;
import com.onevizion.uitest.api.helper.document.Document;
import com.onevizion.uitest.api.helper.dropgrid.DropGrid;
import com.onevizion.uitest.api.helper.entity.EntityClientFile;
import com.onevizion.uitest.api.helper.entity.EntityColor;
import com.onevizion.uitest.api.helper.entity.EntityComponentPackage;
import com.onevizion.uitest.api.helper.entity.EntityConfigApp;
import com.onevizion.uitest.api.helper.entity.EntityConfigAppExt;
import com.onevizion.uitest.api.helper.entity.EntityConfigAppExtParam;
import com.onevizion.uitest.api.helper.entity.EntityConfigField;
import com.onevizion.uitest.api.helper.entity.EntityCoord;
import com.onevizion.uitest.api.helper.entity.EntityDynamicVtable;
import com.onevizion.uitest.api.helper.entity.EntityDynamicVtableValue;
import com.onevizion.uitest.api.helper.entity.EntityIntegration;
import com.onevizion.uitest.api.helper.entity.EntityMenu;
import com.onevizion.uitest.api.helper.entity.EntityMenuItem;
import com.onevizion.uitest.api.helper.entity.EntityReportGroup;
import com.onevizion.uitest.api.helper.entity.EntityRuleType;
import com.onevizion.uitest.api.helper.entity.EntitySecurityRole;
import com.onevizion.uitest.api.helper.entity.EntityTrackorClass;
import com.onevizion.uitest.api.helper.entity.EntityTrackorForm;
import com.onevizion.uitest.api.helper.entity.EntityTrackorTour;
import com.onevizion.uitest.api.helper.entity.EntityTrackorTourStep;
import com.onevizion.uitest.api.helper.entity.EntityTrackorTreeItem;
import com.onevizion.uitest.api.helper.entity.EntityTrackorType;
import com.onevizion.uitest.api.helper.entity.EntityValidation;
import com.onevizion.uitest.api.helper.entity.EntityWpDatePair;
import com.onevizion.uitest.api.helper.entity.EntityWpDiscipline;
import com.onevizion.uitest.api.helper.export.Export;
import com.onevizion.uitest.api.helper.filter.Filter;
import com.onevizion.uitest.api.helper.filter.logic.FilterLogic;
import com.onevizion.uitest.api.helper.formdesigner.FormDesigner;
import com.onevizion.uitest.api.helper.gantt.Gantt;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.grid.group.GridGroup;
import com.onevizion.uitest.api.helper.grid.sort.GridSort;
import com.onevizion.uitest.api.helper.html.input.file.HtmlInputFile;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.mainmenu.MainMenu;
import com.onevizion.uitest.api.helper.map.Map;
import com.onevizion.uitest.api.helper.menu.Menu;
import com.onevizion.uitest.api.helper.notification.Notification;
import com.onevizion.uitest.api.helper.page.button.PageButton;
import com.onevizion.uitest.api.helper.portal.Portal;
import com.onevizion.uitest.api.helper.tab.ListboxTab;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.helper.tree.Tree;
import com.onevizion.uitest.api.helper.userpage.filter.UserpageFilter;
import com.onevizion.uitest.api.helper.view.View;
import com.onevizion.uitest.api.helper.wfvisualeditor.WfVisualEditor;
import com.onevizion.uitest.api.helper.wiki.FckEditor;
import com.onevizion.uitest.api.helper.workplan.Workplan;
import com.onevizion.uitest.api.restapi.CreateProcess;
import com.onevizion.uitest.api.restapi.CreateTest;
import com.onevizion.uitest.api.restapi.CreateTestResult;

public abstract class AbstractSeleniumCore extends AbstractTestNGSpringContextTests {

    /* Helpers Begin */
    @Autowired
    protected Grid2 grid2;

    @Autowired
    protected Tb tb;

    @Autowired
    protected AssertElement assertElement;

    @Autowired
    protected FckEditor fckEditor;

    @Autowired
    protected Js js;

    @Autowired
    protected DualListbox dualListbox;

    @Autowired
    protected Listbox listbox;

    @Autowired
    protected ListboxChatMessage listboxChatMessage;

    @Autowired
    protected ListboxChatSearchMessage listboxChatSearchMessage;

    @Autowired
    protected ListboxChatSubscriber listboxChatSubscriber;

    @Autowired
    protected ListboxTab listboxTab;

    @Autowired
    protected Filter filter;

    @Autowired
    protected Wait wait;

    @Autowired
    protected Tab tab;

    @Autowired
    protected Window window;

    @Autowired
    protected Grid grid;

    @Autowired
    protected Nav nav;

    @Autowired
    protected View view;

    @Autowired
    protected Privilegies privilegies;

    @Autowired
    protected FieldHistory fieldHistory;

    @Autowired
    protected Selector selector;

    @Autowired
    protected Element element;

    @Autowired
    protected ElementWait elementWait;

    @Autowired
    protected Qs qs;

    @Autowired
    protected CloneButton cloneButton;

    @Autowired
    protected Checkbox checkbox;

    @Autowired
    protected MainMenu mainMenu;

    @Autowired
    protected CompAuditLog compAuditLog;

    @Autowired
    protected HtmlSelect htmlSelect;

    @Autowired
    protected UsersSettings usersSettings;

    @Autowired
    protected ElementJs elementJs;

    @Autowired
    protected Login login;

    @Autowired
    protected Logoff logoff;

    @Autowired
    protected Jquery jquery;

    @Autowired
    protected Document document;

    @Autowired
    protected Dashboard dashboard;

    @Autowired
    protected Tree tree;

    @Autowired
    protected Help help;

    @Autowired
    protected Export export;

    @Autowired
    protected FormDesigner formDesigner;

    @Autowired
    protected UserpageFilter userpageFilter;

    @Autowired
    protected WfVisualEditor wfVisualEditor;

    @Autowired
    protected ApiV3Resource apiV3Resource;

    @Autowired
    protected ApiV3Endpoint apiV3Endpoint;

    @Autowired
    protected ApiV3Parameter apiV3Parameter;

    @Autowired
    protected ConfigField configField;

    @Autowired
    protected ColorPicker colorPicker;

    @Autowired
    protected RelationSelector relationSelector;

    @Autowired
    protected Clipboard clipboard;

    @Autowired
    protected Comment comment;

    @Autowired
    protected HtmlInputFile htmlInputFile;

    @Autowired
    protected BplExport bplExport;

    @Autowired
    protected GridSort gridSort;

    @Autowired
    protected Notification notification;

    @Autowired
    protected DropGrid dropGrid;

    @Autowired
    protected Portal portal;

    @Autowired
    protected ShowSql showSql;

    @Autowired
    protected SharePageLink sharePageLink;

    @Autowired
    protected Favorites favorites;

    @Autowired
    protected Chat chat;

    @Autowired
    protected DropDown dropDown;

    @Autowired
    protected GridGroup gridGroup;

    @Autowired
    protected Map map;

    @Autowired
    protected Workplan workplan;

    @Autowired
    protected Gantt gantt;

    @Autowired
    protected PageButton pageButton;

    @Autowired
    protected FilterLogic filterLogic;

    @Autowired
    protected Menu menu;

    @Autowired
    protected ComponentPackage componentPackage;
    /* Helpers End */

    /* Entity Helpers Begin */
    @Autowired
    protected EntityDynamicVtable entityDynamicVtable;

    @Autowired
    protected EntityDynamicVtableValue entityDynamicVtableValue;

    @Autowired
    protected EntityTrackorType entityTrackorType;

    @Autowired
    protected EntityTrackorTreeItem entityTrackorTreeItem;

    @Autowired
    protected EntityMenu entityMenu;

    @Autowired
    protected EntityMenuItem entityMenuItem;

    @Autowired
    protected EntityConfigField entityConfigField;

    @Autowired
    protected EntityComponentPackage entityComponentPackage;

    @Autowired
    protected EntityColor entityColor;

    @Autowired
    protected EntityClientFile entityClientFile;

    @Autowired
    protected EntityReportGroup entityReportGroup;

    @Autowired
    protected EntityTrackorForm entityTrackorForm;

    @Autowired
    protected EntityTrackorTour entityTrackorTour;

    @Autowired
    protected EntityTrackorTourStep entityTrackorTourStep;

    @Autowired
    protected EntityConfigAppExt entityConfigAppExt;

    @Autowired
    protected EntityConfigAppExtParam entityConfigAppExtParam;

    @Autowired
    protected EntityTrackorClass entityTrackorClass;

    @Autowired
    protected EntitySecurityRole entitySecurityRole;

    @Autowired
    protected EntityWpDiscipline entityWpDiscipline;

    @Autowired
    protected EntityWpDatePair entityWpDatePair;

    @Autowired
    protected EntityCoord entityCoord;

    @Autowired
    protected EntityConfigApp entityConfigApp;

    @Autowired
    protected EntityIntegration entityIntegration;

    @Autowired
    protected EntityValidation entityValidation;

    @Autowired
    protected EntityRuleType entityRuleType;
    /* Entity Helpers End */

    @Autowired
    private BrowserCodeCoverage browserCodeCoverage;

    @Autowired
    private CreateTest createTest;

    @Autowired
    private CreateTestResult createTestResult;

    @Autowired
    protected SeleniumSettings seleniumSettings;

    @Autowired
    protected SeleniumScreenshot seleniumScreenshot;

    @Autowired
    protected SeleniumHelper seleniumHelper;

    @Autowired
    protected SeleniumLogger seleniumLogger;

    @Autowired
    private SeleniumNode seleniumNode;

    private String testResultTrackorKey;
    private String testResultNode;

    public static final String GRID_ID_BASE = "gridbox";
    public static final String TREE_ID_BASE = "treeBox";
    public static final String LOADER_ID_BASE = "loader";
    public static final String LOADING_ID_BASE = "loading";
    public static final String LOADING_SPLIT_GRID_RIGHT_ID_BASE = "loadingSplitGridRight";
    public static final String SAVING_ID_BASE = "saving";
    public static final String LOADING_PREVIEW_ID_BASE = "layerLoading";

    public static final String BUTTON_EDIT_ID_BASE = "btnEdit";
    public static final String BUTTON_ADD_ID_BASE = "btnAdd";
    public static final String BUTTON_OK_ID_BASE = "btnOK";
    public static final String BUTTON_START_ID_BASE = "btnStartAt";
    public static final String BUTTON_CANCEL_ID_BASE = "btnCancel";
    public static final String BUTTON_CLOSE_ID_BASE = "btnClose";
    public static final String BUTTON_NEXT_ID_BASE = "btnNext";
    public static final String BUTTON_PRIOR_ID_BASE = "btnPrior";
    public static final String BUTTON_SAVE_GRID_ID_BASE = "btnSaveGrid";

    public static final String BUTTON_ADD_TREE_ID_BASE = "btnAddTree";
    public static final String BUTTON_EDIT_TREE_ID_BASE = "btnEditTree";

    public static final String PREFIX_LOCAL = "L:";
    public static final String PREFIX_GLOBAL = "G:";

    public static final String MESSAGE_WF_NEXT_STEP = "Are you sure you want to move to the Next Step?";
    public static final String MESSAGE_WF_PREV_STEP = "Are you sure you want to move back to the Previous Step?";
    public static final String MESSAGE_APPLY_SUCCESS = "Data successfully submitted.";
    public static final String MESSAGE_DELETE_LOCKABLE = "Warning! You are going to remove \"Lockable\" flag, all existing locks for this field will be removed also. Are you sure you want to proceed?";
    public static final String MESSAGE_DELETE_DROPGRID_CONFIGURATION = "You are going to remove all objects and Trackors created with help of DropGrid. Are you sure you want to continue?";
    public static final String BUTTON_APPLY_ID = "btnApply";
    public static final String CHECKBOX_SELECT_ALL_ID = "SelectCheckboxes";

    public static final String SPECIAL_CHARACTERS_1 = "<";
    public static final String SPECIAL_CHARACTERS_2 = ">";
    public static final String SPECIAL_CHARACTERS_3 = "&";
    public static final String SPECIAL_CHARACTERS_4 = "\"";

    public static final String SPECIAL_CHARACTERS = SPECIAL_CHARACTERS_1 + " " + SPECIAL_CHARACTERS_2 + " " + SPECIAL_CHARACTERS_3 + " " + SPECIAL_CHARACTERS_4;

    public static final String SPECIAL_CHARACTERS_ENCODED_1 = "&lt;";
    public static final String SPECIAL_CHARACTERS_ENCODED_2 = "&gt;";
    public static final String SPECIAL_CHARACTERS_ENCODED_3 = "&amp;";
    public static final String SPECIAL_CHARACTERS_ENCODED_4 = "&quot;";

    public AbstractSeleniumCore() {
        super();
    }

    protected void seleniumOpenBrowserAndLogin(ITestContext context) {
        seleniumSettings.clearWebDriver();
        seleniumSettings.clearUserProperties();
        seleniumSettings.clearWindows();
        seleniumSettings.clearTestUser();
        seleniumSettings.clearTestStatus();
        seleniumSettings.clearTestName();
        seleniumSettings.clearTestLog();
        seleniumSettings.clearTestCallstack();
        seleniumSettings.clearTestFailScreenshot();
        seleniumSettings.clearProfiler();
        seleniumSettings.clearProfilerTestMethods();

        seleniumSettings.setTestName(getTestName());
        seleniumSettings.setTestStatus("success");
        seleniumSettings.setProfiler(new Profiler(getTestName()));

        testResultTrackorKey = createTestResult(context.getSuite().getParameter("test.selenium.processTrackorKey"));

        //System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Firefox Nightly\\firefox.exe");

        try {
            seleniumSettings.getProfiler().start("openBrowser");
            seleniumLogger.info("openBrowser start");
            if (seleniumSettings.getRemoteWebDriver()) {
                Capabilities capabilities = null;
                if (seleniumSettings.getBrowser().equals("firefox")) {
                    capabilities = BrowserFirefox.create(seleniumSettings);
                } else if (seleniumSettings.getBrowser().equals("chrome")) {
                    capabilities = BrowserChrome.create(seleniumSettings);
                } else {
                    throw new SeleniumUnexpectedException("Not support browser " + seleniumSettings.getBrowser());
                }

                //change readTimeout
                //example: selenium wait 3 hours before throw "org.openqa.selenium.remote.UnreachableBrowserException: Error communicating with the remote browser. It may have died."
                //https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/3951
                //https://github.com/SeleniumHQ/selenium/pull/227
                //https://github.com/SeleniumHQ/selenium/pull/285
                try {
                    //OkHttpClient.Factory factory = new OkHttpClient.Factory(Duration.ofMinutes(2), Duration.ofHours(3));
                    //OkHttpClient.Factory factory = new OkHttpClient.Factory(Duration.ofMinutes(3), Duration.ofMinutes(30));
                    OkHttpClient.Factory factory = new OkHttpClient.Factory();
                    factory.builder().connectionTimeout(Duration.ofMinutes(3)).readTimeout(Duration.ofMinutes(30));
                    HttpCommandExecutor executor = new HttpCommandExecutor(Collections.<String, CommandInfo> emptyMap(), new URL("http://" + seleniumSettings.getRemoteAddress() + ":5555/wd/hub"), factory);
                    seleniumSettings.setWebDriver(new RemoteWebDriver(executor, capabilities));
                } catch (MalformedURLException e) {
                    seleniumLogger.error("Unexpected exception: " + e.getMessage());
                }
                //try {
                //    seleniumSettings.setWebDriver(new RemoteWebDriver(new URL("http://" + seleniumSettings.getRemoteAddress() + ":5555/wd/hub"), capability));
                //} catch (MalformedURLException e) {
                //    seleniumLogger.error("Unexpected exception: " + e.getMessage());
                //}

                seleniumSettings.setWebDriver(new Augmenter().augment(seleniumSettings.getWebDriver()));
                testResultNode = seleniumNode.getNodeHostAndPort();
            } else {
                if (seleniumSettings.getBrowser().equals("firefox")) {
                    FirefoxOptions firefoxOptions = BrowserFirefox.create(seleniumSettings);
                    seleniumSettings.setWebDriver(new FirefoxDriver(firefoxOptions));
                } else if (seleniumSettings.getBrowser().equals("chrome")) {
                    ChromeOptions chromeOptions = BrowserChrome.create(seleniumSettings);
                    seleniumSettings.setWebDriver(new ChromeDriver(chromeOptions));
                } else {
                    throw new SeleniumUnexpectedException("Not support browser " + seleniumSettings.getBrowser());
                }
            }

            window.maximize();

            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //not finish or not need when PageLoadStrategy.NONE
            //when PageLoadStrategy.NORMAL
            //in chrome AdminProgramFullAutoCloneTest add submit
            //in chrome AdminProgramFullManualCloneTest add submit
            //[SEVERE]: Timed out receiving message from renderer: 300.000 = 5 minutes
            //new value should be equal or more seleniumSettings.getDefaultTimeout()
            //negative value not working
            //seleniumSettings.getWebDriver().manage().timeouts().pageLoadTimeout(500, TimeUnit.SECONDS);
            //seleniumSettings.getWebDriver().manage().timeouts().setScriptTimeout(500, TimeUnit.SECONDS);

            seleniumSettings.getWebDriver().manage().deleteAllCookies();

            seleniumSettings.setTestUser(context.getCurrentXmlTest().getParameter("test.selenium.user"));

            seleniumSettings.setWindows(new LinkedList<String>());
            seleniumSettings.getWindows().add(seleniumSettings.getWebDriver().getWindowHandle());
            seleniumLogger.info("openBrowser success");

            seleniumSettings.getProfiler().start("codeCoverageStart");
            seleniumLogger.info("codeCoverageStart start");
            browserCodeCoverage.start();
            seleniumLogger.info("codeCoverageStart success");

            seleniumSettings.getProfiler().start("openLoginPage");
            seleniumLogger.info("openLoginPage start");
            document.open2(seleniumSettings.getServerUrl());
            seleniumLogger.info("openLoginPage success");

            seleniumSettings.getProfiler().start("loginIntoSystem");
            seleniumLogger.info("loginIntoSystem start");
            seleniumLogger.info("login as " + seleniumSettings.getTestUser());
            loginIntoSystem(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());
            seleniumLogger.info("loginIntoSystem success");

            seleniumSettings.getProfiler().start("fillGlobalSettings");
            seleniumLogger.info("fillGlobalSettings start");
            fillGlobalSettings();
            seleniumLogger.info("fillGlobalSettings success");

            seleniumSettings.getProfiler().start("fillUserSettings");
            seleniumLogger.info("fillUserSettings start");
            fillUserSettings(seleniumSettings.getTestUser());
            seleniumLogger.info("fillUserSettings success");

            seleniumSettings.getProfiler().start("dataPreparation");
            seleniumLogger.info("dataPreparation start");
            dataPreparation();
            seleniumLogger.info("dataPreparation success");

            seleniumSettings.setProfilerTestMethods(new Profiler(getTestName()));

            seleniumSettings.getProfiler().start("openInternalPage");
            seleniumSettings.getProfilerTestMethods().start("openInternalPage");
            seleniumLogger.info("openInternalPage start");
            openInternalPage();
            seleniumLogger.info("openInternalPage success");
        } catch (Throwable e) {
            seleniumSettings.setTestStatus("fail");

            seleniumLogger.error("seleniumOpenBrowserAndLogin fail");
            seleniumLogger.error("seleniumOpenBrowserAndLogin Unexpected exception: " + e.getMessage(), e);

            if (seleniumSettings.getWebDriver() != null) {
                seleniumHelper.closeAfterErrorAndGetScreenshot();
            }

            throw new SeleniumUnexpectedException(e);
        }
    }

    protected void seleniumCloseBrowser(ITestContext context) {
        if (seleniumSettings.getProfilerTestMethods() != null) {
            seleniumSettings.getProfilerTestMethods().stop();
        }

        try {
            if (seleniumSettings.getWebDriver() != null) {
                seleniumSettings.getProfiler().start("codeCoverageFinish");
                seleniumLogger.info("codeCoverageFinish start");
                browserCodeCoverage.finish();
                seleniumLogger.info("codeCoverageFinish success");

                seleniumSettings.getProfiler().start("closeBrowser");
                seleniumLogger.info("closeBrowser start");
                seleniumHelper.closeAfterError();
                seleniumSettings.getWebDriver().quit();
                seleniumLogger.info("closeBrowser success");
            }

            seleniumSettings.getProfiler().stop();

            updateTestResult(context.getSuite().getParameter("test.selenium.processTrackorKey"), testResultTrackorKey);
        } catch (Throwable e) {
            seleniumSettings.setTestStatus("fail");

            seleniumLogger.error("seleniumCloseBrowser fail");
            seleniumLogger.error("seleniumCloseBrowser Unexpected exception: " + e.getMessage());

            seleniumSettings.getProfiler().stop();

            updateTestResult(context.getSuite().getParameter("test.selenium.processTrackorKey"), testResultTrackorKey);

            throw new SeleniumUnexpectedException(e);
        }
    }

    protected abstract void fillGlobalSettings();

    protected abstract void fillUserSettings(String userName);

    protected abstract void loginIntoSystem(String user, String password);

    protected abstract void dataPreparation();

    protected abstract void openInternalPage();

    protected abstract String getModuleName();

    private String getTestName() {
        String fullTestName = getFullTestName();
        return fullTestName.substring(fullTestName.lastIndexOf('.') + 1);
    }

    private String getFullTestName() {
        return getClass().getName();
    }

    public static Long getGridIdx() {
        return 0L;
    }

    public static Long getTreeIdx() {
        return 0L;
    }

    @Deprecated
    public static void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void correctSleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String createTestResult(String processTrackorKey) {
        if (seleniumSettings.getRestApiUrl().isEmpty() || seleniumSettings.getRestApiCredential().isEmpty()) {
            return null;
        }

        try {
            createTest.createOrUpdate(getTestName(), getFullTestName(), getModuleName(), getBugs());
            return createTestResult.create(processTrackorKey, getTestName(), getBugs());
        } catch (Exception e) {
            seleniumLogger.error("call REST API Unexpected exception: " + e.getMessage());
        }

        return null;
    }

    private void updateTestResult(String processTrackorKey, String testResultTrackorKey) {
        if (seleniumSettings.getRestApiUrl().isEmpty() || seleniumSettings.getRestApiCredential().isEmpty()) {
            return;
        }

        if (testResultTrackorKey == null) {
            return;
        }

        if (seleniumSettings.getWebDriver() != null) {
            String browserVersion = ((HasCapabilities) seleniumSettings.getWebDriver()).getCapabilities().getVersion();
            try {
                CreateProcess.updateBrowserVersion(seleniumSettings.getRestApiUrl(), seleniumSettings.getRestApiCredential(), processTrackorKey, browserVersion);
            } catch (Exception e) {
                seleniumLogger.error("call REST API Unexpected exception: " + e.getMessage());
            }
        }

        try {
            createTestResult.update(testResultTrackorKey, seleniumSettings.getTestStatus(), testResultNode, seleniumSettings.getTestLog(),
                    seleniumSettings.getProfiler(), seleniumSettings.getProfilerTestMethods(),
                    getErrorReport(), seleniumSettings.getTestCallstack(), seleniumSettings.getTestFailScreenshot());
        } catch (Exception e) {
            seleniumLogger.error("call REST API Unexpected exception: " + e.getMessage());
        }
    }

    private String getBugs() {
        Class<?> testClass;
        try {
            testClass = Class.forName(getFullTestName());
        } catch (ClassNotFoundException e) {
            return "Error";
        }

        SeleniumBug[] seleniumBugs = testClass.getAnnotationsByType(SeleniumBug.class);

        StringBuilder strBuilder = new StringBuilder();
        for (SeleniumBug seleniumBug : seleniumBugs) {
            if (strBuilder.length() == 0) {
                strBuilder.append(seleniumBug.value());
            } else {
                strBuilder.append("," + seleniumBug.value());
            }
        }

        return strBuilder.toString();
    }

    protected abstract String getErrorReport();

}