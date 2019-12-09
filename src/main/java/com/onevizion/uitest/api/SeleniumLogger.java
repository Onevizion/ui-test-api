package com.onevizion.uitest.api;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

@Component
public class SeleniumLogger {

    private final Logger logger = LoggerFactory.getLogger(SeleniumLogger.class);

    @Resource
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
        msg = msg.replaceAll("\\\\", "\\\\\\\\");
        msg = msg.replaceAll("\\n", "\\\\n");
        msg = msg.replaceAll("\\t", "\\\\t");
        msg = msg.replaceAll("\\r", "\\\\r");
        msg = msg.replaceAll("\"", "'");

        String testLog = seleniumSettings.getTestLog();
        if (testLog == null) {
            testLog = msg;
        } else {
            testLog = testLog + "\\n" + msg;
        }
        seleniumSettings.setTestLog(testLog);
    }

    private void callstack(String msg) {
        msg = msg.replaceAll("\\\\", "\\\\\\\\");
        msg = msg.replaceAll("\\n", "\\\\n");
        msg = msg.replaceAll("\\t", "\\\\t");
        msg = msg.replaceAll("\\r", "\\\\r");
        msg = msg.replaceAll("\"", "'");

        if (seleniumSettings.getTestCallstack() == null) {
            seleniumSettings.setTestCallstack(msg);
        }
    }

}