package com.onevizion.uitest.api;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class SeleniumListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.info(test.seleniumSettings.getTestName() + " method " + tr.getName() + " start");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.info(test.seleniumSettings.getTestName() + " method " + tr.getName() + " success");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.error(test.seleniumSettings.getTestName() + " method " + tr.getName() + " fail");
        test.seleniumSettings.setTestStatus("fail");
        test.seleniumHelper.closeAfterErrorAndGetScreenshot();
    }

}