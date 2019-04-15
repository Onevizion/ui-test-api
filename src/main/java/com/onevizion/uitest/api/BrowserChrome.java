package com.onevizion.uitest.api;

import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class BrowserChrome {

    public static ChromeOptions create(SeleniumSettings seleniumSettings) {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("safebrowsing.enabled", "true");
        chromePrefs.put("download.default_directory", seleniumSettings.getUploadFilesPath());

        //TODO workaround for chrome 52
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList("--disable-translate", "--always-authorize-plugins"));
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        options.setExperimentalOption("prefs", chromePrefs);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        if (seleniumSettings.getHeadlessMode()) {
            options.setProxy(null);
            options.addArguments("--headless");
            options.addArguments("--deterministic-fetch");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--no-proxy-server");
            options.addArguments("--proxy-server='direct://'");
            options.addArguments("--proxy-bypass-list=*");
        }

        return options;
    }

}