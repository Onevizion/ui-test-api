package com.onevizion.uitest.api;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class SeleniumListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.info("method " + tr.getName() + " start");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.info("method " + tr.getName() + " success");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.error("method " + tr.getName() + " fail");

        test.seleniumLogger.error("method " + tr.getName() + " fail Unexpected exception: " + tr.getThrowable().getMessage(), tr.getThrowable());
        test.seleniumSettings.setTestStatus("fail");
        test.seleniumHelper.closeAfterErrorAndGetScreenshot();
    }

}