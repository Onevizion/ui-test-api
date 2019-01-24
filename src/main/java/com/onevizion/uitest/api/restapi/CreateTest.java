package com.onevizion.uitest.api.restapi;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class CreateTest {

    private static final String TRACKOR_TYPE_NAME = "SELENIUM_TEST";

    @Resource
    private SeleniumSettings seleniumSettings;

    public void create(String testName) {
        boolean isExist = checkTestAlreadyExist(testName);

        if (isExist) {
            return;
        }

        createTest(testName);
    }

    private boolean checkTestAlreadyExist(String testName) {
        boolean isExist;
        try {
            URL url = new URL(seleniumSettings.getRestApiUrl() + "/api/v3/trackor_types/" + TRACKOR_TYPE_NAME + "/trackors/search?fields=XITOR_KEY");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + seleniumSettings.getRestApiCredential());

            String input = "equal(XITOR_KEY, \"\\\"" + testName + "\\\"\")";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new SeleniumUnexpectedException("CreateTest.checkTestAlreadyExist Failed : HTTP error code : " + conn.getResponseCode() + " HTTP error message : " + conn.getResponseMessage());
            }

            String result = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8.toString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);

            if (jsonNode.isArray()) {
                if (jsonNode.size() == 0) {
                    isExist = false;
                } else if (jsonNode.size() == 1) {
                    isExist = true;
                } else {
                    throw new SeleniumUnexpectedException("Found many tests");
                }
            } else {
                throw new SeleniumUnexpectedException("Result not array");
            }

            conn.disconnect();

            return isExist;
        } catch (Exception e) {
            throw new SeleniumUnexpectedException(e);
        }
    }

    private void createTest(String testName) {
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
                    "     \"XITOR_KEY\": \"" + testName + "\" " + 
                    "   } " + 
                    " }";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new SeleniumUnexpectedException("CreateTest.createTest Failed : HTTP error code : " + conn.getResponseCode() + " HTTP error message : " + conn.getResponseMessage());
            }

            conn.disconnect();
        } catch (Exception e) {
            throw new SeleniumUnexpectedException(e);
        }
    }

}