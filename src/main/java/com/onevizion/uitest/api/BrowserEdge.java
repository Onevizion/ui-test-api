package com.onevizion.uitest.api;

import java.util.HashMap;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.CapabilityType;

public class BrowserEdge {

    public static CustomEdgeOptions create(SeleniumSettings seleniumSettings) {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("safebrowsing.enabled", "true");
        chromePrefs.put("download.default_directory", seleniumSettings.getUploadFilesPath());
        HashMap<String, Object> chromeDownloadPrefs = new HashMap<>();
        chromeDownloadPrefs.put("default_directory", seleniumSettings.getUploadFilesPath());
        chromePrefs.put("download", chromeDownloadPrefs);

        CustomEdgeOptions options = new CustomEdgeOptions();
        options.setBinary("C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\Edge SxS\\Application\\msedge.exe");

        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        options.setExperimentalOption("prefs", chromePrefs);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        if (seleniumSettings.getHeadlessMode()) {
            options.addArguments("--headless");
        }

        return options;
    }

}