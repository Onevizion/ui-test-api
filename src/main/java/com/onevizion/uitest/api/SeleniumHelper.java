package com.onevizion.uitest.api;

import javax.annotation.Resource;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
public class SeleniumHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    @Resource
    private SeleniumScreenshot seleniumScreenshot;

    @Resource
    private Js js;

    void closeAfterErrorAndGetScreenshot() {
        closeAllAlertsOnWindow();
        resetAllChangesOnWindow();
        seleniumScreenshot.getScreenshot(true);

        closeAllWindows();
        closeAllAlertsOnWindow();
        resetAllChangesOnWindow();
        getErrorReportFromWindow();
    }

    void closeAfterError() {
        closeAllWindows();
        closeAllAlertsOnWindow();
        resetAllChangesOnWindow();
        getErrorReportFromWindow();
    }

    private void closeAllAlertsOnWindow() {
        int maxAlertsCount = 100; //protection from the endless cycle
        while (maxAlertsCount > 0) {
            maxAlertsCount = maxAlertsCount - 1;
            try {
                Alert alert = seleniumSettings.getWebDriver().switchTo().alert();
                seleniumLogger.error(seleniumSettings.getTestName() +  " There is alert with error message: " + alert.getText());
                alert.accept();
            } catch (WebDriverException e) { // should be NoAlertPresentException
                break;
            }
        }

        if (maxAlertsCount == 0) {
            seleniumLogger.error(seleniumSettings.getTestName() + " Window with title: " + seleniumSettings.getWebDriver().getTitle() + " have endless alerts");
        }
    }

    private void resetAllChangesOnWindow() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1151
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1434872
        js.resetFormChange();
        js.resetGridChange();
    }

    private void closeAllWindows() {
        if (seleniumSettings.getWindows() == null) {
            return;
        }

        try {
            while (seleniumSettings.getWindows().size() > 1) {
                closeAllAlertsOnWindow();
                resetAllChangesOnWindow();
                getErrorReportFromWindow();

                final int currentWindowsCount = seleniumSettings.getWebDriver().getWindowHandles().size();
                String title = seleniumSettings.getWebDriver().getTitle();

                seleniumLogger.error(seleniumSettings.getTestName() + " There is window with title: " + seleniumSettings.getWebDriver().getTitle());

                seleniumSettings.getWebDriver().get("about:blank");
                seleniumSettings.getWebDriver().close();

                new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                    .withMessage("Waiting for closing modal window with title=[" + title + "] failed.")
                    .until(webdriver -> webdriver.getWindowHandles().size() == currentWindowsCount - 1);

                seleniumSettings.getWindows().remove(seleniumSettings.getWindows().size() - 1);
                seleniumSettings.getWebDriver().switchTo().window(seleniumSettings.getWindows().get(seleniumSettings.getWindows().size() - 1));

                new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                    .withMessage("Waiting for closing modal window.")
                    .until(webdriver -> !js.isWindowClosed());
            }
        } catch (Exception e) {
            seleniumLogger.error(seleniumSettings.getTestName() + " closeAllWindows Unexpected exception: " + e.getMessage());
        }
    }

    private void getErrorReportFromWindow() {
        String prefix = "Error Report ID: ";
        int uuidLength = 36;

        String pageText = js.getPageText();
        int startIndex = pageText.indexOf(prefix);
        if (startIndex > -1) {
            int errorReportStartIndex = startIndex;
            int errorReportEndIndex = errorReportStartIndex + prefix.length() + uuidLength;
            String errorReport = pageText.substring(errorReportStartIndex, errorReportEndIndex);
            seleniumLogger.error(seleniumSettings.getTestName() + " Window with title: " + seleniumSettings.getWebDriver().getTitle() + " have error report: " + errorReport);
        }
    }

}