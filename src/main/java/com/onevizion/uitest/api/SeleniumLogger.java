package com.onevizion.uitest.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

@Component
public class SeleniumLogger {

    private final Logger logger = LoggerFactory.getLogger(SeleniumLogger.class);

    @Autowired
    private SeleniumSettings seleniumSettings;

    public void trace(String msg) {
        logger.trace(seleniumSettings.getTestName() + " " + msg);
        Reporter.log(seleniumSettings.getTestName() + " " + msg);
        log(msg);
    }

    public void debug(String msg) {
        logger.debug(seleniumSettings.getTestName() + " " + msg);
        Reporter.log(seleniumSettings.getTestName() + " " + msg);
        log(msg);
    }

    public void info(String msg) {
        logger.info(seleniumSettings.getTestName() + " " + msg);
        Reporter.log(seleniumSettings.getTestName() + " " + msg);
        log(msg);
    }

    public void warn(String msg) {
        logger.warn(seleniumSettings.getTestName() + " " + msg);
        Reporter.log(seleniumSettings.getTestName() + " " + msg);
        log(msg);
    }

    public void error(String msg) {
        logger.error(seleniumSettings.getTestName() + " " + msg);
        Reporter.log(seleniumSettings.getTestName() + " " + msg);
        log(msg);
    }

    public void error(String msg, Throwable t) {
        logger.error(seleniumSettings.getTestName() + " " + msg);
        Reporter.log(seleniumSettings.getTestName() + " " + msg);
        log(msg);
        callstack(ExceptionUtils.getStackTrace(t));
    }

    private void log(String msg) {
        String escapedMsg = OnevizionUtils.escapeStringForRestApiV3(msg);
        escapedMsg = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + " " + escapedMsg;

        String testLog = seleniumSettings.getTestLog();
        if (testLog == null) {
            testLog = escapedMsg;
        } else {
            testLog = testLog + "\\n" + escapedMsg;
        }
        seleniumSettings.setTestLog(testLog);
    }

    private void callstack(String msg) {
        if (seleniumSettings.getTestCallstack() == null) {
            String escapedMsg = OnevizionUtils.escapeStringForRestApiV3(msg);
            seleniumSettings.setTestCallstack(escapedMsg);
        }
    }

}