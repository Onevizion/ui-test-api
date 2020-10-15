package com.onevizion.uitest.api.restapi;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.profiler.Profiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onevizion.uitest.api.OnevizionUtils;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class CreateTestResult {

    private static final String TRACKOR_TYPE_NAME = "SELENIUM_TEST_RESULT";
    private static final String PARENT_TRACKOR_TYPE_NAME_TEST = "SELENIUM_TEST";
    private static final String PARENT_TRACKOR_TYPE_NAME_PROCESS = "SELENIUM_PROCESS";

    @Autowired
    private SeleniumSettings seleniumSettings;

    public String create(String process, String testName, String bugs) {
        try {
            URL url = new URL(seleniumSettings.getRestApiUrl() + "/api/v3/trackor_types/" + TRACKOR_TYPE_NAME + "/trackors");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + seleniumSettings.getRestApiCredential());

            String input = "{ " + 
                    "   \"fields\": { " + 
                    "     \"STR_STATUS\": \"in progress\", " + 
                    "     \"STR_BUGS\": \"" + bugs + "\" " + 
                    "   }, " + 
                    "   \"parents\": [ " + 
                    "     { " + 
                    "       \"trackor_type\": \"" + PARENT_TRACKOR_TYPE_NAME_TEST + "\", " + 
                    "       \"filter\": { " + 
                    "         \"XITOR_KEY\": \"\\\"" + testName + "\\\"\", " + 
                    "         \"ST_TYPE\": \"\\\"web\\\"\" " + 
                    "       } " + 
                    "     }, " + 
                    "     { " + 
                    "       \"trackor_type\": \"" + PARENT_TRACKOR_TYPE_NAME_PROCESS + "\", " + 
                    "       \"filter\": { " + 
                    "         \"XITOR_KEY\": \"\\\"" + process + "\\\"\" " + 
                    "       } " + 
                    "     } " + 
                    "   ] " + 
                    " }";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new SeleniumUnexpectedException("CreateTestResult.create Failed : HTTP error code : " + conn.getResponseCode() + " HTTP error message : " + conn.getResponseMessage());
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

    public void update(String trackorKey, String testStatus, String testResultNode, String testLog,
            Profiler profiler, Profiler profilerTestMethods,
            String errorReport, String errorCallstack, String errorScreenshot) {
        try {
            String profilerOutput = OnevizionUtils.escapeStringForRestApiV3(profiler.toString());

            String profilerTestMethodsOutput = "";
            double runtimeTestMethods = -1;
            if (profilerTestMethods != null) {
                profilerTestMethodsOutput = OnevizionUtils.escapeStringForRestApiV3(profilerTestMethods.toString());

                runtimeTestMethods = profilerTestMethods.elapsedTime();
                runtimeTestMethods = (double) runtimeTestMethods / 1_000_000_000;
                runtimeTestMethods = (double) Math.round(runtimeTestMethods * 1000) / 1000;
            }

            URL url = new URL(seleniumSettings.getRestApiUrl() + "/api/v3/trackor_types/" + TRACKOR_TYPE_NAME + "/trackors?" + TRACKOR_TYPE_NAME +".TRACKOR_KEY=%22" + trackorKey + "%22");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + seleniumSettings.getRestApiCredential());

            String input;
            if ("fail".equals(testStatus)) {
                if (errorScreenshot != null) {
                    input = "{ " + 
                            "   \"fields\": { " + 
                            "     \"STR_STATUS\": \"" + testStatus + "\", " + 
                            "     \"STR_NODE\": \"" + testResultNode + "\", " + 
                            "     \"STR_ERROR_LOG\": \"" + testLog + "\", " + 
                            "     \"STR_PROFILER\": \"" + profilerOutput + "\", " + 
                            "     \"STR_PROFILER_TEST_METHODS\": \"" + profilerTestMethodsOutput + "\", " + 
                            "     \"STR_RUNTIME_TEST_METHO_SECONDS\": \"" + runtimeTestMethods + "\", " + 
                            "     \"STR_ERROR_REPORT\": \"" + errorReport + "\", " + 
                            "     \"STR_ERROR_CALLSTACK\": \"" + errorCallstack + "\", " + 
                            "     \"STR_ERROR_FILE\": {\"file_name\": \"screenshot.jpg\", \"data\": \"" + OnevizionUtils.escapeStringForRestApiV3(errorScreenshot) + "\"} " + 
                            "   } " + 
                            " }";
                } else {
                    input = "{ " + 
                            "   \"fields\": { " + 
                            "     \"STR_STATUS\": \"" + testStatus + "\", " + 
                            "     \"STR_NODE\": \"" + testResultNode + "\", " + 
                            "     \"STR_ERROR_LOG\": \"" + testLog + "\", " + 
                            "     \"STR_PROFILER\": \"" + profilerOutput + "\", " + 
                            "     \"STR_PROFILER_TEST_METHODS\": \"" + profilerTestMethodsOutput + "\", " + 
                            "     \"STR_RUNTIME_TEST_METHO_SECONDS\": \"" + runtimeTestMethods + "\", " + 
                            "     \"STR_ERROR_REPORT\": \"" + errorReport + "\", " + 
                            "     \"STR_ERROR_CALLSTACK\": \"" + errorCallstack + "\" " + 
                            "   } " + 
                            " }";
                }
            } else {
                input = "{ " + 
                        "   \"fields\": { " + 
                        "     \"STR_STATUS\": \"" + testStatus + "\", " + 
                        "     \"STR_NODE\": \"" + testResultNode + "\", " + 
                        "     \"STR_ERROR_LOG\": \"" + testLog + "\", " + 
                        "     \"STR_PROFILER\": \"" + profilerOutput + "\", " + 
                        "     \"STR_PROFILER_TEST_METHODS\": \"" + profilerTestMethodsOutput + "\", " + 
                        "     \"STR_RUNTIME_TEST_METHO_SECONDS\": \"" + runtimeTestMethods + "\" " + 
                        "   } " + 
                        " }";
            }

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new SeleniumUnexpectedException("CreateTestResult.update Failed : HTTP error code : " + conn.getResponseCode() + " HTTP error message : " + conn.getResponseMessage());
            }

            conn.disconnect();
        } catch (Exception e) {
            throw new SeleniumUnexpectedException(e);
        }
    }

}