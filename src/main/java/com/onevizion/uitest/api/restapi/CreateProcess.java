package com.onevizion.uitest.api.restapi;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class CreateProcess {

    private CreateProcess() {
        throw new IllegalStateException("Utility class");
    }

    private static final String TRACKOR_TYPE_NAME = "Selenium_Process";

    public static void create(String restApiUrl, String restApiCredential, String browserName, String date, String duration, int testsCount) {
        try {
            URL url = new URL(restApiUrl + "/api/v3/trackor_types/" + TRACKOR_TYPE_NAME + "/trackors");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + restApiCredential);

            String input = "{ " + 
                    "   \"fields\": { " + 
                    "     \"SPRC_BROWSER\": \"" + browserName + "\", " + 
                    "     \"SPRC_START_DATE\": \"" + date + "\", " + 
                    "     \"SPRC_DURATION\": \"" + duration + "\", " + 
                    "     \"SPRC_TESTS_COUNT\": \"" + testsCount + "\" " + 
                    "   } " + 
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