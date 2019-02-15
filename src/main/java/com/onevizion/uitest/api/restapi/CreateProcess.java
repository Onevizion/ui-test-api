package com.onevizion.uitest.api.restapi;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class CreateProcess {

    private CreateProcess() {
        throw new IllegalStateException("Utility class");
    }

    private static final String TRACKOR_TYPE_NAME = "SELENIUM_PROCESS";

    public static String create(String restApiUrl, String restApiCredential) {
        try {
            URL url = new URL(restApiUrl + "/api/v3/trackor_types/" + TRACKOR_TYPE_NAME + "/trackors");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + restApiCredential);

            String input = "{ " + 
                    " }";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new SeleniumUnexpectedException("CreateProcess.create Failed : HTTP error code : " + conn.getResponseCode() + " HTTP error message : " + conn.getResponseMessage());
            }

            String result = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8.toString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);
            String trackorKey = jsonNode.path("TRACKOR_KEY").asText();

            conn.disconnect();

            return trackorKey;
        } catch (Exception e) {
            throw new SeleniumUnexpectedException(e);
        }
    }

    public static void update(String restApiUrl, String restApiCredential, String restApiVersion, String processTrackorKey, String browserName, String date, String duration, int testsCount) {
        try {
            URL url = new URL(restApiUrl + "/api/v3/trackor_types/" + TRACKOR_TYPE_NAME + "/trackors?" + TRACKOR_TYPE_NAME +".TRACKOR_KEY=" + processTrackorKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + restApiCredential);

            String input = "{ " + 
                    "   \"fields\": { " + 
                    "     \"SPRC_VERSION\": \"" + restApiVersion + "\", " + 
                    "     \"SPRC_BROWSER\": \"" + browserName + "\", " + 
                    "     \"SPRC_START_DATE\": \"" + date + "\", " + 
                    "     \"SPRC_DURATION\": \"" + duration + "\", " + 
                    "     \"SPRC_TESTS_COUNT\": \"" + testsCount + "\" " + 
                    "   } " + 
                    " }";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new SeleniumUnexpectedException("CreateProcess.update Failed : HTTP error code : " + conn.getResponseCode() + " HTTP error message : " + conn.getResponseMessage());
            }

            conn.disconnect();
        } catch (Exception e) {
            throw new SeleniumUnexpectedException(e);
        }
    }

}