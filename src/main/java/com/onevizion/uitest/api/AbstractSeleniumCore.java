package com.onevizion.uitest.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.Alert;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.internal.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.Reporter;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.CloneButton;
import com.onevizion.uitest.api.helper.CompAuditLog;
import com.onevizion.uitest.api.helper.DualListbox;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementJs;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.FieldHistory;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.GridRowButton;
import com.onevizion.uitest.api.helper.Help;
import com.onevizion.uitest.api.helper.HtmlInput;
import com.onevizion.uitest.api.helper.HtmlSelect;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Login;
import com.onevizion.uitest.api.helper.Logoff;
import com.onevizion.uitest.api.helper.Nav;
import com.onevizion.uitest.api.helper.NewDropDown;
import com.onevizion.uitest.api.helper.Privilegies;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Qs;
import com.onevizion.uitest.api.helper.RelationSelector;
import com.onevizion.uitest.api.helper.Sort;
import com.onevizion.uitest.api.helper.Tb;
import com.onevizion.uitest.api.helper.UsersSettings;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.api.v3.ApiV3Endpoint;
import com.onevizion.uitest.api.helper.api.v3.ApiV3Parameter;
import com.onevizion.uitest.api.helper.api.v3.ApiV3Resource;
import com.onevizion.uitest.api.helper.bpl.export.BplExport;
import com.onevizion.uitest.api.helper.clipboard.Clipboard;
import com.onevizion.uitest.api.helper.colorpicker.ColorPicker;
import com.onevizion.uitest.api.helper.comment.Comment;
import com.onevizion.uitest.api.helper.configfield.ConfigField;
import com.onevizion.uitest.api.helper.dashboard.Dashboard;
import com.onevizion.uitest.api.helper.document.Document;
import com.onevizion.uitest.api.helper.entity.EntityClientFile;
import com.onevizion.uitest.api.helper.entity.EntityColor;
import com.onevizion.uitest.api.helper.entity.EntityComponentPackage;
import com.onevizion.uitest.api.helper.entity.EntityConfigAppExt;
import com.onevizion.uitest.api.helper.entity.EntityConfigAppExtParam;
import com.onevizion.uitest.api.helper.entity.EntityConfigField;
import com.onevizion.uitest.api.helper.entity.EntityDynamicVtable;
import com.onevizion.uitest.api.helper.entity.EntityDynamicVtableValue;
import com.onevizion.uitest.api.helper.entity.EntityMenu;
import com.onevizion.uitest.api.helper.entity.EntityMenuItem;
import com.onevizion.uitest.api.helper.entity.EntityReportGroup;
import com.onevizion.uitest.api.helper.entity.EntitySecurityRole;
import com.onevizion.uitest.api.helper.entity.EntityTrackorClass;
import com.onevizion.uitest.api.helper.entity.EntityTrackorForm;
import com.onevizion.uitest.api.helper.entity.EntityTrackorTour;
import com.onevizion.uitest.api.helper.entity.EntityTrackorTourStep;
import com.onevizion.uitest.api.helper.entity.EntityTrackorTreeItem;
import com.onevizion.uitest.api.helper.entity.EntityTrackorType;
import com.onevizion.uitest.api.helper.entity.EntityWpDatePair;
import com.onevizion.uitest.api.helper.entity.EntityWpDiscipline;
import com.onevizion.uitest.api.helper.export.Export;
import com.onevizion.uitest.api.helper.filter.Filter;
import com.onevizion.uitest.api.helper.formdesigner.FormDesigner;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.html.input.file.HtmlInputFile;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.mainmenu.MainMenu;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.helper.tree.Tree;
import com.onevizion.uitest.api.helper.userpage.filter.UserpageFilter;
import com.onevizion.uitest.api.helper.view.View;
import com.onevizion.uitest.api.helper.wfvisualeditor.WfVisualEditor;
import com.onevizion.uitest.api.restapi.CreateTest;
import com.onevizion.uitest.api.restapi.CreateTestResult;

public abstract class AbstractSeleniumCore extends AbstractTestNGSpringContextTests {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumCore.class);

    /* Helpers Begin */
    @Resource
    protected Grid2 grid2;

    @Resource
    protected Tb tb;

    @Resource
    protected AssertElement assertElement;

    @Resource
    protected Js js;

    @Resource
    protected DualListbox dualListbox;

    @Resource
    protected Filter filter;

    @Resource
    protected Wait wait;

    @Resource
    protected Tab tab;

    @Resource
    protected Window window;

    @Resource
    protected Grid grid;

    @Resource
    protected Sort sort;

    @Resource
    protected Nav nav;

    @Resource
    protected View view;

    @Resource
    protected Privilegies privilegies;

    @Resource
    protected FieldHistory fieldHistory;

    @Resource
    protected PsSelector psSelector;

    @Resource
    protected Element element;

    @Resource
    protected ElementWait elementWait;

    @Resource
    protected Qs qs;

    @Resource
    protected CloneButton cloneButton;

    @Resource
    protected GridRowButton gridRowButton;

    @Resource
    protected Checkbox checkbox;

    @Resource
    protected MainMenu mainMenu;

    @Resource
    protected CompAuditLog compAuditLog;

    @Resource
    protected HtmlSelect htmlSelect;

    @Resource
    protected UsersSettings usersSettings;

    @Resource
    protected ElementJs elementJs;

    @Resource
    protected Login login;

    @Resource
    protected Logoff logoff;

    @Resource
    protected Jquery jquery;

    @Resource
    protected Document document;

    @Resource
    protected Dashboard dashboard;

    @Resource
    protected Tree tree;

    @Resource
    protected Help help;

    @Resource
    protected Export export;

    @Resource
    protected FormDesigner formDesigner;

    @Resource
    protected UserpageFilter userpageFilter;

    @Resource
    protected HtmlInput htmlInput;

    @Resource
    protected WfVisualEditor wfVisualEditor;

    @Resource
    protected ApiV3Resource apiV3Resource;

    @Resource
    protected ApiV3Endpoint apiV3Endpoint;

    @Resource
    protected ApiV3Parameter apiV3Parameter;

    @Resource
    protected NewDropDown newDropDown;

    @Resource
    protected ConfigField configField;

    @Resource
    protected ColorPicker colorPicker;

    @Resource
    protected RelationSelector relationSelector;

    @Resource
    protected Clipboard clipboard;

    @Resource
    protected Comment comment;

    @Resource
    protected HtmlInputFile htmlInputFile;

    @Resource
    protected BplExport bplExport;
    /* Helpers End */

    /* Entity Helpers Begin */
    @Resource
    protected EntityDynamicVtable entityDynamicVtable;

    @Resource
    protected EntityDynamicVtableValue entityDynamicVtableValue;

    @Resource
    protected EntityTrackorType entityTrackorType;

    @Resource
    protected EntityTrackorTreeItem entityTrackorTreeItem;

    @Resource
    protected EntityMenu entityMenu;

    @Resource
    protected EntityMenuItem entityMenuItem;

    @Resource
    protected EntityConfigField entityConfigField;

    @Resource
    protected EntityComponentPackage entityComponentPackage;

    @Resource
    protected EntityColor entityColor;

    @Resource
    protected EntityClientFile entityClientFile;

    @Resource
    protected EntityReportGroup entityReportGroup;

    @Resource
    protected EntityTrackorForm entityTrackorForm;

    @Resource
    protected EntityTrackorTour entityTrackorTour;

    @Resource
    protected EntityTrackorTourStep entityTrackorTourStep;

    @Resource
    protected EntityConfigAppExt entityConfigAppExt;

    @Resource
    protected EntityConfigAppExtParam entityConfigAppExtParam;

    @Resource
    protected EntityTrackorClass entityTrackorClass;

    @Resource
    protected EntitySecurityRole entitySecurityRole;

    @Resource
    protected EntityWpDiscipline entityWpDiscipline;

    @Resource
    protected EntityWpDatePair entityWpDatePair;
    /* Entity Helpers End */

    @Resource
    private CreateTest createTest;

    @Resource
    private CreateTestResult createTestResult;

    private DesiredCapabilities capability;

    @Resource
    public SeleniumSettings seleniumSettings;

    @Resource
    public SeleniumScreenshot seleniumScreenshot;

    private Date startDate;

    public static final String GRID_ID_BASE = "gridbox";
    public static final String TREE_ID_BASE = "treeBox";
    public static final String LOADING_ID_BASE = "loading";
    public static final String LOADING_SPLIT_GRID_RIGHT_ID_BASE = "loadingSplitGridRight";
    public static final String SAVING_ID_BASE = "saving";

    public static final String BUTTON_EDIT_ID_BASE = "btnEdit";
    public static final String BUTTON_DELETE_ID_BASE = "btnDelete";
    public static final String BUTTON_ADD_ID_BASE = "btnAdd";
    public static final String BUTTON_OK_ID_BASE = "btnOK";
    public static final String BUTTON_START_ID_BASE = "btnStartAt";
    public static final String BUTTON_CANCEL_ID_BASE = "btnCancel";
    public static final String BUTTON_CLOSE_ID_BASE = "btnClose";
    public static final String BUTTON_NEXT_ID_BASE = "btnNext";
    public static final String BUTTON_PRIOR_ID_BASE = "btnPrior";
    public static final String BUTTON_SAVE_GRID_ID_BASE = "btnSaveGrid";
    public static final String BUTTON_EXPORT_ID_BASE = "btnExport";
    public static final String BUTTON_REPORT_WIZARD_ID_BASE = "btnReportWizard";
    public static final String BUTTON_REORDER_ID_BASE = "btnReorder";

    public static final String BUTTON_ADD_TREE_ID_BASE = "btnAddTree";
    public static final String BUTTON_EDIT_TREE_ID_BASE = "btnEditTree";
    public static final String BUTTON_DELETE_TREE_ID_BASE = "btnDeleteTree";
    public static final String BUTTON_UP_TREE_ID_BASE = "btnUpTree";
    public static final String BUTTON_DOWN_TREE_ID_BASE = "btnDownTree";

    public static final String PREFIX_LOCAL = "L:";
    public static final String PREFIX_GLOBAL = "G:";

    public static final String MESSAGE_WF_NEXT_STEP = "Are you sure you want to move to the Next Step?";
    public static final String MESSAGE_WF_PREV_STEP = "Are you sure you want to move back to the Previous Step?";
    public static final String MESSAGE_APPLY_SUCCESS = "Data successfully submitted.";
    public static final String MESSAGE_DELETE_LOCKABLE = "Warning! You are going to remove \"Lockable\" flag, all existing locks for this field will be removed also. Are you sure you want to proceed?";
    public static final String BUTTON_APPLY_ID = "btnApply";
    public static final String CHECKBOX_SELECT_ALL_ID = "SelectCheckboxes";

    public static final String BUTTON_RULES_ID_BASE = "btnRules";

    public static final String RELATION_ID_BASE = "lbParentsChildren";
    public static final String BUTTON_RELATION_ID_BASE = "btnParentsChildren";

    public static final String BUTTON_TASKS_ID_BASE = "btnTasks";
    public static final String BUTTON_WF_ID_BASE = "btnWF";

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
        Calendar cal = Calendar.getInstance();
        startDate = cal.getTime();

        seleniumSettings.setTestName(getTestName());
        seleniumSettings.setTestStatus("success");

        //System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Firefox Nightly\\firefox.exe");

        try {
            fillGlobalSettings();

            if (seleniumSettings.getRemoteWebDriver()) {
                if (seleniumSettings.getBrowser().equals("firefox")) {
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("dom.disable_beforeunload", false); //TODO BUG IN W3C https://github.com/w3c/webdriver/issues/1294
                    profile.setPreference("dom.successive_dialog_time_limit", 0);
                    profile.setPreference("dom.max_script_run_time", 1000);
                    profile.setPreference("browser.download.folderList", 2);
                    profile.setPreference("browser.download.dir", seleniumSettings.getUploadFilesPath().replaceAll("\\\\", "\\\\\\\\"));
                    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/xml");
                    //profile.setPreference("security.sandbox.content.level", 5);
                    //profile.setEnableNativeEvents(false); /* TODO selenium 5374 issue */

                    FirefoxOptions options = new FirefoxOptions();
                    options.setPageLoadStrategy(PageLoadStrategy.NONE);
                    //options.addPreference("security.sandbox.content.level", 5);
                    //options.setLegacy(true);
                    options.setProfile(profile);
                    //TODO https://github.com/mozilla/geckodriver/issues/617
                    //https://bugzilla.mozilla.org/show_bug.cgi?id=1264259
                    //options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                    //options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                    //options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                    capability = DesiredCapabilities.firefox();
                    capability.setBrowserName(seleniumSettings.getBrowser());
                    capability.merge(options);
                } else if (seleniumSettings.getBrowser().equals("chrome")) {
                    HashMap<String, Object> chromePrefs = new HashMap<>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    chromePrefs.put("safebrowsing.enabled", "true");
                    chromePrefs.put("download.default_directory", seleniumSettings.getUploadFilesPath());
                    capability = DesiredCapabilities.chrome();
                    capability.setBrowserName(seleniumSettings.getBrowser());
                    capability.setCapability("chrome.switches", Arrays.asList("--disable-translate", "--always-authorize-plugins"));

                    //TODO workaround for chrome 52
                    ChromeOptions options = new ChromeOptions();
                    options.setPageLoadStrategy(PageLoadStrategy.NONE);
                    options.addArguments("--no-sandbox");
                    options.setExperimentalOption("prefs", chromePrefs);
                    capability.setCapability(ChromeOptions.CAPABILITY, options);
                } else if (seleniumSettings.getBrowser().equals("internet explorer 11")) {
                    capability = DesiredCapabilities.internetExplorer();
                    capability.setCapability("ie.ensureCleanSession", true);
                    capability.setBrowserName("internet explorer");
                    capability.setVersion("11");
                } else if (seleniumSettings.getBrowser().equals("internet explorer 8")) {
                    capability = DesiredCapabilities.internetExplorer();
                    capability.setCapability("ie.validateCookieDocumentType", false);
                    capability.setCapability("ie.ensureCleanSession", true);
                    capability.setBrowserName("internet explorer");
                    capability.setVersion("8");
                }

                //change readTimeout
                //example: selenium wait 3 hours before throw "org.openqa.selenium.remote.UnreachableBrowserException: Error communicating with the remote browser. It may have died."
                //https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/3951
                //https://github.com/SeleniumHQ/selenium/pull/227
                //https://github.com/SeleniumHQ/selenium/pull/285
                try {
                    //OkHttpClient.Factory factory = new OkHttpClient.Factory(Duration.ofMinutes(2), Duration.ofHours(3));
                    OkHttpClient.Factory factory = new OkHttpClient.Factory(Duration.ofMinutes(2), Duration.ofMinutes(30));
                    HttpCommandExecutor executor = new HttpCommandExecutor(Collections.<String, CommandInfo> emptyMap(), new URL("http://" + seleniumSettings.getRemoteAddress() + ":5555/wd/hub"), factory);
                    seleniumSettings.setWebDriver(new RemoteWebDriver(executor, capability));
                } catch (MalformedURLException e) {
                    logger.error("{} Unexpected exception: {}", seleniumSettings.getTestName(), e.getMessage());
                }
                //try {
                //    seleniumSettings.setWebDriver(new RemoteWebDriver(new URL("http://" + seleniumSettings.getRemoteAddress() + ":5555/wd/hub"), capability));
                //} catch (MalformedURLException e) {
                //    logger.error(seleniumSettings.getTestName() + " Unexpected exception: " + e.getMessage());
                //}

                seleniumSettings.setWebDriver(new Augmenter().augment(seleniumSettings.getWebDriver()));
            } else {
                if (seleniumSettings.getBrowser().equals("firefox")) {
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("dom.disable_beforeunload", false); //TODO BUG IN W3C https://github.com/w3c/webdriver/issues/1294
                    profile.setPreference("dom.successive_dialog_time_limit", 0);
                    profile.setPreference("dom.max_script_run_time", 100);
                    profile.setPreference("browser.download.folderList", 2);
                    profile.setPreference("browser.download.dir", seleniumSettings.getUploadFilesPath().replaceAll("\\\\", "\\\\\\\\"));
                    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/xml");
                    //profile.setEnableNativeEvents(false); /* TODO selenium 5374 issue */

                    FirefoxOptions options = new FirefoxOptions();
                    //options.setLogLevel(FirefoxDriverLogLevel.TRACE);
                    //options.setLegacy(true);
                    options.setPageLoadStrategy(PageLoadStrategy.NONE);
                    options.setProfile(profile);
                    //TODO https://github.com/mozilla/geckodriver/issues/617
                    //https://bugzilla.mozilla.org/show_bug.cgi?id=1264259
                    //options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                    //options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                    //options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                    seleniumSettings.setWebDriver(new FirefoxDriver(options));
                } else if (seleniumSettings.getBrowser().equals("chrome")) {
                    HashMap<String, Object> chromePrefs = new HashMap<>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    chromePrefs.put("safebrowsing.enabled", "true");
                    chromePrefs.put("download.default_directory", seleniumSettings.getUploadFilesPath());
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments(Arrays.asList("--disable-translate", "--always-authorize-plugins"));
                    options.setPageLoadStrategy(PageLoadStrategy.NONE);
                    options.setExperimentalOption("prefs", chromePrefs);

                    if (seleniumSettings.getHeadlessMode()) {
                        options.addArguments("--headless");
                        options.addArguments("window-size=1024x768");
                    }

                    seleniumSettings.setWebDriver(new ChromeDriver(options));
                } else if (seleniumSettings.getBrowser().equals("internet explorer 11")) {
                    capability = DesiredCapabilities.internetExplorer();
                    capability.setCapability("ie.ensureCleanSession", true);
                    capability.setBrowserName("internet explorer");
                    capability.setVersion("11");
                    seleniumSettings.setWebDriver(new InternetExplorerDriver(capability));
                } else if (seleniumSettings.getBrowser().equals("internet explorer 8")) {
                    capability = DesiredCapabilities.internetExplorer();
                    capability.setCapability("ie.validateCookieDocumentType", false);
                    capability.setCapability("ie.ensureCleanSession", true);
                    capability.setBrowserName("internet explorer");
                    capability.setVersion("8");
                    seleniumSettings.setWebDriver(new InternetExplorerDriver(capability));
                }
            }

            seleniumSettings.getWebDriver().manage().window().maximize();

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

            logger.info("{} browser open", seleniumSettings.getTestName());
            document.open2(seleniumSettings.getServerUrl());

            String newTestUser = context.getCurrentXmlTest().getParameter("test.selenium.user");
            seleniumSettings.setTestUser(newTestUser);

            logger.info("{} login as {}", seleniumSettings.getTestName(), seleniumSettings.getTestUser());
            login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());

            dataPreparation();

            openInternalPage();

            seleniumSettings.setWindows(new LinkedList<String>());
            seleniumSettings.getWindows().add(seleniumSettings.getWebDriver().getWindowHandle());

        } catch (Exception e) {
            seleniumSettings.setTestStatus("fail");

            logger.error("{} openBrowserAndLogin fail", seleniumSettings.getTestName());
            logger.error("{} openBrowserAndLogin Unexpected exception: {}", seleniumSettings.getTestName(), e.getMessage());

            seleniumScreenshot.getScreenshot();

            throw new SeleniumUnexpectedException(e);
        }
    }

    protected void seleniumCloseBrowser() {
        try {
            if (seleniumSettings.getWebDriver() != null) {
                //TODO following code can throw exception if alert present. remove this code after remove firefox 59 bug
                int maxAlertsCount = 100; //protection from the endless cycle
                while (maxAlertsCount > 0) {
                    maxAlertsCount = maxAlertsCount - 1;
                    try {
                        Alert alert = seleniumSettings.getWebDriver().switchTo().alert();
                        logger.error("{} closeBrowser There is alert with error message: {}", seleniumSettings.getTestName(), alert.getText());
                        Reporter.log(seleniumSettings.getTestName() + " closeBrowser There is alert with error message: " + alert.getText());
                        alert.accept();
                    } catch (WebDriverException e) { // should be NoAlertPresentException
                        break;
                    }
                }

                if (maxAlertsCount == 0) {
                    logger.error("{} Window with title: {} have endless alerts", seleniumSettings.getTestName(), seleniumSettings.getWebDriver().getTitle());
                    Reporter.log(seleniumSettings.getTestName() + " Window with title: " + seleniumSettings.getWebDriver().getTitle() + " have endless alerts");
                }

                //TODO firefox 59 bug
                //https://github.com/mozilla/geckodriver/issues/1151
                //https://bugzilla.mozilla.org/show_bug.cgi?id=1434872
                js.resetFormChange();
                js.resetGridChange();
                seleniumSettings.getWebDriver().quit();
                logger.info("{} browser close", seleniumSettings.getTestName());
            }

            Calendar cal = Calendar.getInstance();
            Date finishDate = cal.getTime();
            long duration = finishDate.getTime() - startDate.getTime();
            long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            String durationMinutesStr = Long.toString(durationMinutes);
            logger.info("{} executed in {} minutes", seleniumSettings.getTestName(), durationMinutesStr);

            saveTestResult(durationMinutesStr);
        } catch (Exception e) {
            seleniumSettings.setTestStatus("fail");

            logger.error("{} closeBrowser fail", seleniumSettings.getTestName());
            logger.error("{} closeBrowser Unexpected exception: {}", seleniumSettings.getTestName(), e.getMessage());

            Calendar cal = Calendar.getInstance();
            Date finishDate = cal.getTime();
            long duration = finishDate.getTime() - startDate.getTime();
            long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            String durationMinutesStr = Long.toString(durationMinutes);
            logger.info("{} executed in {} minutes", seleniumSettings.getTestName(), durationMinutesStr);

            saveTestResult(durationMinutesStr);

            throw new SeleniumUnexpectedException(e);
        }

        if (seleniumSettings.getBrowser().equals("internet explorer 11") || seleniumSettings.getBrowser().equals("internet explorer 8")) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                logger.error("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    protected abstract void fillGlobalSettings();

    protected abstract void login(String user, String password);

    protected abstract void dataPreparation();

    protected abstract void openInternalPage();

    private String getTestName() {
        String testName = getClass().getName();
        testName = testName.substring(testName.lastIndexOf('.') + 1);
        return testName;
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
            logger.error("Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }

    private void saveTestResult(String durationMinutesStr) {
        if (seleniumSettings.getRestApiUrl().isEmpty() || seleniumSettings.getRestApiCredential().isEmpty()) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cal.getTime());

        try {
            createTest.create(getTestName());
            createTestResult.create(getTestName(), seleniumSettings.getBrowser(), date, seleniumSettings.getTestStatus(), durationMinutesStr);
        } catch (Exception e) {
            logger.error("{} call REST API Unexpected exception: {}", seleniumSettings.getTestName(), e.getMessage());
        }
    }

}