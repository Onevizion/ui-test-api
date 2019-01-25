package com.onevizion.uitest.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.onevizion.uitest.api.helper.Js;

public class SeleniumListener extends TestListenerAdapter {

    private Js js = new Js();

    @Override
    public void onTestStart(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);

        test.seleniumLogger.info(getTestName(tr) + " method " + tr.getName() + " start");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);

        test.seleniumLogger.info(getTestName(tr) + " method " + tr.getName() + " success");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);

        test.seleniumLogger.error(getTestName(tr) + " method " + tr.getName() + " fail");

        test.seleniumSettings.setTestStatus("fail");

        try {
            String screensDirectory = tr.getTestContext().getSuite().getParameter("test.selenium.screenshotsPath");
            String ciAddress = tr.getTestContext().getSuite().getParameter("test.selenium.ciAddr");
            Boolean isRemoteWebDriver = Boolean.valueOf(tr.getTestContext().getSuite().getParameter("test.selenium.remoteWebDriver"));

            try {
                Alert alert = test.seleniumSettings.getWebDriver().switchTo().alert();
                test.seleniumLogger.error(getTestName(tr) + " There is alert with error message: " + alert.getText());
                alert.accept();
            } catch (WebDriverException e) { // should be NoAlertPresentException
                
            }

            if (test.seleniumSettings.getWebDriver() instanceof TakesScreenshot) {
                String browserName;
                if (isRemoteWebDriver) {
                    browserName = test.seleniumSettings.getBrowser();
                } else {
                    browserName = test.seleniumSettings.getWebDriver().getClass().getName();
                    browserName = browserName.substring(browserName.lastIndexOf('.') + 1);
                }
                browserName = browserName.replaceAll(" ", "_");

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                Date date = new Date();

                String screenshotBase64 = ((TakesScreenshot) test.seleniumSettings.getWebDriver()).getScreenshotAs(OutputType.BASE64);
                test.seleniumSettings.setTestFailScreenshot(screenshotBase64);
                byte[] screen = ((TakesScreenshot) test.seleniumSettings.getWebDriver()).getScreenshotAs(OutputType.BYTES);

                try {
                    String screenFileName = String.format("%s_%s_%s.jpg", browserName, getTestName(tr), dateFormat.format(date));
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
                    test.seleniumLogger.error(getTestName(tr) + " " + screenAddr);
                } catch (IOException e) {
                    test.seleniumLogger.error(getTestName(tr) + " Can't save screenshot because of: " + e.getMessage());
                }
            } else {
                test.seleniumLogger.error(getTestName(tr) + " Current web browser dont't supports getting screenshots");
            }
            test.seleniumLogger.error(getTestName(tr) + " Unexpected exception: " + tr.getThrowable().getMessage());
        } catch (Exception e) {
            test.seleniumLogger.error(getTestName(tr) + " Unexpected exception: " + e.getMessage());
        }

        //TODO
        //close all alerts and windows (except main window)
        //because webDriver.quit() not wait for all windows are closed
        try {
            while (test.seleniumSettings.getWindows().size() > 1) {
                int maxAlertsCount = 100; //protection from the endless cycle
                while (maxAlertsCount > 0) {
                    maxAlertsCount = maxAlertsCount - 1;
                    try {
                        Alert alert = test.seleniumSettings.getWebDriver().switchTo().alert();
                        test.seleniumLogger.error(getTestName(tr) + " There is alert with error message: " + alert.getText());
                        alert.accept();
                    } catch (WebDriverException e) { // should be NoAlertPresentException
                        break;
                    }
                }

                if (maxAlertsCount == 0) {
                    test.seleniumLogger.error(getTestName(tr) + " Window with title: " + test.seleniumSettings.getWebDriver().getTitle() + " have endless alerts");
                }

                final int currentWindowsCount = test.seleniumSettings.getWebDriver().getWindowHandles().size();
                String title = test.seleniumSettings.getWebDriver().getTitle();

                test.seleniumLogger.error(getTestName(tr) + " There is window with title: " + test.seleniumSettings.getWebDriver().getTitle());

                //TODO firefox 59 bug
                //https://github.com/mozilla/geckodriver/issues/1151
                //https://bugzilla.mozilla.org/show_bug.cgi?id=1434872
                js.resetFormChange(test);
                js.resetGridChange(test);
                test.seleniumSettings.getWebDriver().get("about:blank");
                test.seleniumSettings.getWebDriver().close();

                new WebDriverWait(test.seleniumSettings.getWebDriver(), test.seleniumSettings.getDefaultTimeout())
                    .withMessage("Waiting for closing modal window with title=[" + title + "] failed.")
                    .until(webdriver -> webdriver.getWindowHandles().size() == currentWindowsCount - 1);

                test.seleniumSettings.getWindows().remove(test.seleniumSettings.getWindows().size() - 1);
                test.seleniumSettings.getWebDriver().switchTo().window(test.seleniumSettings.getWindows().get(test.seleniumSettings.getWindows().size() - 1));

                new WebDriverWait(test.seleniumSettings.getWebDriver(), test.seleniumSettings.getDefaultTimeout())
                    .withMessage("Waiting for closing modal window.")
                    .until(webdriver -> !js.isWindowClosed(test));
            }
        } catch (Exception e) {
            test.seleniumLogger.error(getTestName(tr) + " Unexpected exception: " + e.getMessage());
        }
    }

    private String getTestName(ITestResult tr){
        String testName = tr.getTestClass().getName();
        testName = testName.substring(testName.lastIndexOf('.') + 1);
        return testName;
    }

}