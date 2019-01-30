package com.onevizion.uitest.api;

import javax.annotation.Resource;

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
        logger.trace(msg);
        Reporter.log(msg);
        log(msg);
    }

    public void debug(String msg) {
        logger.debug(msg);
        Reporter.log(msg);
        log(msg);
    }

    public void info(String msg) {
        logger.info(msg);
        Reporter.log(msg);
        log(msg);
    }

    public void warn(String msg) {
        logger.warn(msg);
        Reporter.log(msg);
        log(msg);
    }

    public void error(String msg) {
        logger.error(msg);
        Reporter.log(msg);
        log(msg);
    }

    private void log(String msg) {
        msg = msg.replaceAll("\\\\", "\\\\\\\\");
        msg = msg.replaceAll("\\n", "\\\\n");
        msg = msg.replaceAll("\\t", "\\\\t");
        msg = msg.replaceAll("\"", "'");
        String testLog = seleniumSettings.getTestLog();
        if (testLog == null) {
            testLog = msg;
        } else {
            testLog = testLog + "\\n" + msg;
        }
        seleniumSettings.setTestLog(testLog);
    }

}