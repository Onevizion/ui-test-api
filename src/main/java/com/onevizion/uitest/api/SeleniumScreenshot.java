package com.onevizion.uitest.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.stereotype.Component;

@Component
public class SeleniumScreenshot {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    public void getScreenshot() {
        getScreenshot(false);
    }

    public void getScreenshot(boolean saveScreenshotToExternalSystem) {
        try {
            String screensDirectory = seleniumSettings.getScreenshotsPath();
            String ciAddress = seleniumSettings.getCiAddr();
            Boolean isRemoteWebDriver = seleniumSettings.getRemoteWebDriver();

            if (seleniumSettings.getWebDriver() instanceof TakesScreenshot) {
                String browserName;
                if (isRemoteWebDriver) {
                    browserName = seleniumSettings.getBrowser();
                } else {
                    browserName = seleniumSettings.getWebDriver().getClass().getName();
                    browserName = browserName.substring(browserName.lastIndexOf('.') + 1);
                }
                browserName = browserName.replaceAll(" ", "_");

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
                Date date = new Date();

                if (saveScreenshotToExternalSystem) {
                    String screenshotBase64 = ((TakesScreenshot) seleniumSettings.getWebDriver()).getScreenshotAs(OutputType.BASE64);
                    seleniumSettings.setTestFailScreenshot(screenshotBase64);
                }

                byte[] screen = ((TakesScreenshot) seleniumSettings.getWebDriver()).getScreenshotAs(OutputType.BYTES);
                String screenFileName = String.format("%s_%s_%s.jpg", browserName, seleniumSettings.getTestName(), dateFormat.format(date));

                try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(String.format("%s/%s", screensDirectory, screenFileName))))) {
                    out.write(screen);
                    String screenAddr;
                    if (isRemoteWebDriver) {
                        screenAddr = ciAddress + screenFileName;
                    } else {
                        screenAddr = screensDirectory + screenFileName;
                    }
                    seleniumLogger.error(seleniumSettings.getTestName() + " " + screenAddr);
                } catch (IOException e) {
                    seleniumLogger.error(seleniumSettings.getTestName() + " Can't save screenshot because of: " + e.getMessage());
                }
            } else {
                seleniumLogger.error(seleniumSettings.getTestName() + " Current web browser dont't supports getting screenshots");
            }
        } catch (Exception e) {
            seleniumLogger.error(seleniumSettings.getTestName() + " getScreenshot Unexpected exception: " + e.getMessage());
        }
    }

}