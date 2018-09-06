package com.onevizion.guitest;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class SeleniumSettings {

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    private ThreadLocal<UserProperties> userProperties = new ThreadLocal<UserProperties>();

    private ThreadLocal<List<String>> windows = new ThreadLocal<List<String>>();

    private ThreadLocal<String> testUser = new ThreadLocal<String>();

    private ThreadLocal<String> testStatus = new ThreadLocal<String>();

    private ThreadLocal<String> testName = new ThreadLocal<String>();

    @Resource
    private Long defaultTimeout; //need

    @Resource
    private String uploadFilesPath; //need

    @Resource
    private String remoteAddress; //need

    @Resource
    private Boolean remoteWebDriver; //need

    @Resource
    private String screenshotsPath; //need

    @Resource
    private String ciAddr; //need

    @Resource
    private String testPassword; //need

    @Resource
    private String testPasswordApiV3; //need

    @Resource
    private String browser; //need

    @Resource
    private String restApiCredential; //need

    @Resource
    private String restApiUrl; //need

    @Resource
    private String serverUrl; //need

    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    void setWebDriver(WebDriver webDriver) {
        this.webDriver.set(webDriver);
    }

    public UserProperties getUserProperties() {
        return userProperties.get();
    }

    void setUserProperties(UserProperties userProperties) {
        this.userProperties.set(userProperties);
    }

    public List<String> getWindows() {
        return windows.get();
    }

    void setWindows(List<String> windows) {
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

    public Long getDefaultTimeout() {
        return defaultTimeout;
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
 
    public String getTestPasswordApiV3() {
        return testPasswordApiV3;
    }

    public Boolean getRemoteWebDriver() {
        return remoteWebDriver;
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

    public String getServerUrl() {
        return serverUrl;
    }

}