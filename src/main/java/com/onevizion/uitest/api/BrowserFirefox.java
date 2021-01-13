package com.onevizion.uitest.api;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

public class BrowserFirefox {

    public static FirefoxOptions create(SeleniumSettings seleniumSettings) {
//        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Firefox Nightly\\firefox.exe");
//        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\firefox_today_13\\firefox.exe");

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("marionette.actors.enabled", false); //TODO https://bugzilla.mozilla.org/show_bug.cgi?id=1685951
        options.addPreference("dom.disable_beforeunload", false); //TODO BUG IN W3C https://github.com/w3c/webdriver/issues/1294
        options.addPreference("dom.successive_dialog_time_limit", 0);
        options.addPreference("dom.max_script_run_time", 1000);
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", seleniumSettings.getUploadFilesPath().replaceAll("\\\\", "\\\\\\\\"));
        options.addPreference("browser.download.viewableInternally.enabledTypes", "");
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "text/xml");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        //options.setLegacy(true);
        //options.setLogLevel(FirefoxDriverLogLevel.TRACE);
        //TODO https://github.com/mozilla/geckodriver/issues/617
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1264259
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        options.setHeadless(seleniumSettings.getHeadlessMode());

        return options;
    }

}