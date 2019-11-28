package com.onevizion.uitest.api;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserEdge {

    public static EdgeOptions create(SeleniumSettings seleniumSettings) {
//        //https://stackoverflow.com/questions/58590613/msedgedriver-chromium-does-not-work-after-version-79-0-309-0
//
//        System.setProperty("webdriver.edge.driver", "C:\\workspace2\\msedgedriver.exe");
//        //System.setProperty("webdriver.edge.bin", "C:\\Program Files (x86)\\Microsoft\\Edge Dev\\Application\\msedge.exe");
//        //System.setProperty("webdriver.edge.bin", "C:\\Users\\Администратор\\AppData\\Local\\Microsoft\\Edge SxS\\Application\\msedge.exe");
//
//        ChromeOptions chromeOptions = new ChromeOptions();
//        //chromeOptions.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge Dev\\Application\\msedge.exe");
//        chromeOptions.setBinary("C:\\Users\\Администратор\\AppData\\Local\\Microsoft\\Edge SxS\\Application\\msedge.exe");
//        EdgeOptions options = new EdgeOptions();
//        options.merge(chromeOptions);
//
//        return options;

        System.setProperty("webdriver.edge.driver", "C:\\selenium\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        return options;
    }

}