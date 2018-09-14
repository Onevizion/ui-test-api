package com.onevizion.uitest.api.restapi;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class CreateTestResult {

    private final static String TRACKOR_TYPE_NAME = "SELENIUM_TEST_RESULT";
    private final static String PARENT_TRACKOR_TYPE_NAME = "SELENIUM_TEST";

    @Resource
    private SeleniumSettings seleniumSettings;

    public void create(String testName, String browserName, String date, String testStatus, String duration) {
        try {
            URL url = new URL(seleniumSettings.getRestApiUrl() + "/api/v3/trackor_types/" + TRACKOR_TYPE_NAME + "/trackors");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + seleniumSettings.getRestApiCredential());

            String input = "{ " + 
                    "   \"fields\": { " + 
                    "     \"STR_DATE\": \"" + date + "\", " + 
                    "     \"STR_BROWSER\": \"" + browserName + "\", " + 
                    "     \"STR_STATUS\": \"" + testStatus + "\", " + 
                    "     \"STR_DURATION\": \"" + duration + "\" " + 
                    "   }, " + 
                    "   \"parents\": [ " + 
                    "     { " + 
                    "       \"trackor_type\": \"" + PARENT_TRACKOR_TYPE_NAME + "\", " + 
                    "       \"filter\": { " + 
                    "         \"XITOR_KEY\": \"" + testName + "\" " + 
                    "       } " + 
                    "     } " + 
                    "   ] " + 
                    " }";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new SeleniumUnexpectedException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            conn.disconnect();
        } catch (Exception e) {
            throw new SeleniumUnexpectedException(e);
        }
    }

}