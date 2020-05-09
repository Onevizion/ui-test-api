package com.onevizion.uitest.api;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.slf4j.profiler.Profiler;
import org.springframework.stereotype.Component;

@Component
public class SeleniumSettings {

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private ThreadLocal<UserProperties> userProperties = new ThreadLocal<>();

    private ThreadLocal<List<String>> windows = new ThreadLocal<>();

    private ThreadLocal<String> testUser = new ThreadLocal<>();

    private ThreadLocal<String> testStatus = new ThreadLocal<>();

    private ThreadLocal<String> testName = new ThreadLocal<>();

    private ThreadLocal<String> testLog = new ThreadLocal<>();

    private ThreadLocal<String> testCallstack = new ThreadLocal<>();

    private ThreadLocal<String> testFailScreenshot = new ThreadLocal<>();

    private ThreadLocal<Profiler> profiler = new ThreadLocal<>();

    private ThreadLocal<Profiler> profilerTestMethods = new ThreadLocal<>();

    @Resource
    private Long defaultTimeout;

    @Resource
    private String uploadFilesPath;

    @Resource
    private String remoteAddress;

    @Resource
    private Boolean remoteWebDriver;

    @Resource
    private Boolean headlessMode;

    @Resource
    private Boolean codeCoverage;

    @Resource
    private String screenshotsPath;

    @Resource
    private String ciAddr;

    @Resource
    private String testPassword;

    @Resource
    private String browser;

    @Resource
    private String restApiCredential;

    @Resource
    private String restApiUrl;

    @Resource
    private String restApiVersion;

    @Resource
    private String serverUrl;

    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver.set(webDriver);
    }

    public UserProperties getUserProperties() {
        return userProperties.get();
    }

    public void setUserProperties(UserProperties userProperties) {
        this.userProperties.set(userProperties);
    }

    public List<String> getWindows() {
        return windows.get();
    }

    public void setWindows(List<String> windows) {
        this.windows.set(windows);
    }

    public String getTestUser() {
        return testUser.get();
    }

    void setTestUser(String testUser) {
        this.testUser.set(testUser);
    }

    public String getTestStatus() {
        return testStatus.get();
    }

    void setTestStatus(String testStatus) {
        this.testStatus.set(testStatus);
    }

    public String getTestName() {
        return testName.get();
    }

    void setTestName(String testName) {
        this.testName.set(testName);
    }

    void clearTestLog() {
        testLog.remove();
    }

    public String getTestLog() {
        return testLog.get();
    }

    void setTestLog(String testLog) {
        this.testLog.set(testLog);
    }

    void clearTestCallstack() {
        testCallstack.remove();
    }

    public String getTestCallstack() {
        return testCallstack.get();
    }

    void setTestCallstack(String testCallstack) {
        this.testCallstack.set(testCallstack);
    }

    void clearTestFailScreenshot() {
        testFailScreenshot.remove();
    }

    public String getTestFailScreenshot() {
        return testFailScreenshot.get();
    }

    void setTestFailScreenshot(String testFailScreenshot) {
        this.testFailScreenshot.set(testFailScreenshot);
    }

    void clearProfiler() {
        profiler.remove();
    }

    public Profiler getProfiler() {
        return profiler.get();
    }

    void setProfiler(Profiler profiler) {
        this.profiler.set(profiler);
    }

    void clearProfilerTestMethods() {
        profilerTestMethods.remove();
    }

    public Profiler getProfilerTestMethods() {
        return profilerTestMethods.get();
    }

    void setProfilerTestMethods(Profiler profilerTestMethods) {
        this.profilerTestMethods.set(profilerTestMethods);
    }

    public Long getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(Long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public String getUploadFilesPath() {
        return uploadFilesPath;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getTestPassword() {
        return testPassword;
    }

    public Boolean getRemoteWebDriver() {
        return remoteWebDriver;
    }

    public Boolean getHeadlessMode() {
        return headlessMode;
    }

    public void setHeadlessMode(Boolean headlessMode) {
        this.headlessMode = headlessMode;
    }

    public Boolean getCodeCoverage() {
        return codeCoverage;
    }

    public String getScreenshotsPath() {
        return screenshotsPath;
    }

    public String getCiAddr() {
        return ciAddr;
    }

    public String getBrowser() {
        return browser;
    }

    public String getRestApiCredential() {
        return restApiCredential;
    }

    public String getRestApiUrl() {
        return restApiUrl;
    }

    public String getRestApiVersion() {
        return restApiVersion;
    }

    public String getServerUrl() {
        return serverUrl;
    }

}