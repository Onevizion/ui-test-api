package com.onevizion.guitest;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

@Component
public class SeleniumScreenshot {

    private final Logger logger = LoggerFactory.getLogger(SeleniumScreenshot.class);

    @Resource
    private SeleniumSettings seleniumSettings;

    public void getScreenshot() {
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

                byte[] screen = ((TakesScreenshot) seleniumSettings.getWebDriver()).getScreenshotAs(OutputType.BYTES);

                try {
                    String screenFileName = String.format("%s_%s_%s.jpg", browserName, seleniumSettings.getTestName(), dateFormat.format(date));
                    new File(screensDirectory).mkdirs();
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(String.format("%s/%s", screensDirectory, screenFileName))));
                    out.write(screen);
                    out.close();
                    String screenAddr;
                    if (isRemoteWebDriver) {
                        screenAddr = ciAddress + screenFileName;
                    } else {
                        screenAddr = screensDirectory + screenFileName;
                    }
                    logger.error(seleniumSettings.getTestName() + " " + screenAddr);
                    Reporter.log(seleniumSettings.getTestName() + " " + screenAddr);
                } catch (IOException e) {
                    logger.error(seleniumSettings.getTestName() + " Can't save screenshot because of: " + e.getMessage());
                    Reporter.log(seleniumSettings.getTestName() + " Can't save screenshot because of: " + e.getMessage());
                }
            } else {
                logger.error(seleniumSettings.getTestName() + " Current web browser dont't supports getting screenshots");
                Reporter.log(seleniumSettings.getTestName() + " Current web browser dont't supports getting screenshots");
            }
        } catch (Exception e) {
            logger.error(seleniumSettings.getTestName() + " Unexpected exception: " + e.getMessage());
            Reporter.log(seleniumSettings.getTestName() + " Unexpected exception: " + e.getMessage());
        }
    }

}