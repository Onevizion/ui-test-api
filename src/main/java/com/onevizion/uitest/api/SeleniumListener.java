package com.onevizion.uitest.api;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class SeleniumListener extends TestListenerAdapter {

    private ThreadLocal<Date> startDate = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult tr) {
        startDate.set(Calendar.getInstance().getTime());

        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.info("method " + tr.getName() + " start");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        long duration = TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTime().getTime() - startDate.get().getTime());

        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.info("method " + tr.getName() + " success elapsed time " + duration + " seconds");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        long duration = TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTime().getTime() - startDate.get().getTime());

        AbstractSeleniumCore test = ((AbstractSeleniumCore) tr.getTestClass().getInstances(false)[0]);
        test.seleniumLogger.error("method " + tr.getName() + " fail elapsed time " + duration + " seconds");

        test.seleniumLogger.error("method " + tr.getName() + " fail Unexpected exception: " + tr.getThrowable().getMessage(), tr.getThrowable());
        test.seleniumSettings.setTestStatus("fail");
        test.seleniumHelper.closeAfterErrorAndGetScreenshot();
    }

}