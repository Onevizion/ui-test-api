package com.onevizion.uitest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.springframework.stereotype.Component;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class BrowserCodeCoverage {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    private ThreadLocal<String> wsUrl = new ThreadLocal<>();

    private ThreadLocal<WebSocket> ws = new ThreadLocal<>();

    private ThreadLocal<Object> waitCoordinator = new ThreadLocal<Object>() {
        @Override
        public Object initialValue() {
            return new Object();
        }
    };

    public void start() {
        if (skipCodeCoverage()) {
            return;
        }

        ws.remove();

        SessionId sessionId = ((RemoteWebDriver) seleniumSettings.getWebDriver()).getSessionId();
        String host = getHost(seleniumSettings.getRemoteAddress(), sessionId);
        wsUrl.set(getWebSocketDebuggerUrl(host));

        try {
            seleniumLogger.error("coverage sendWSMessage 1");
            sendWSMessage(wsUrl.get(), "{\"id\":1, \"method\":\"Profiler.enable\"}");
        } catch (Exception e) {
            seleniumLogger.error("exception in coverage sendWSMessage 1 " + e.getMessage());
        }

        try {
            seleniumLogger.error("coverage sendWSMessage 2");
            sendWSMessage(wsUrl.get(), "{\"id\":2, \"method\":\"Profiler.startPreciseCoverage\", \"params\":{\"callCount\":true, \"detailed\":true}}");
        } catch (Exception e) {
            seleniumLogger.error("exception in coverage sendWSMessage 2 " + e.getMessage());
        }
    }

    public void finish() {
        if (skipCodeCoverage()) {
            return;
        }

        try {
            seleniumLogger.error("coverage sendWSMessage 3");
            sendWSMessage(wsUrl.get(), "{\"id\":3, \"method\":\"Profiler.takePreciseCoverage\"}");
        } catch (Exception e) {
            seleniumLogger.error("exception in coverage sendWSMessage 3 " + e.getMessage());
        }

        try {
            seleniumLogger.error("coverage sendWSMessage 4");
            sendWSMessage(wsUrl.get(), "{\"id\":4, \"method\":\"Profiler.stopPreciseCoverage\"}");
        } catch (Exception e) {
            seleniumLogger.error("exception in coverage sendWSMessage 4 " + e.getMessage());
        }

        ws.get().disconnect();
    }

    private boolean skipCodeCoverage() {
        if (!seleniumSettings.getBrowser().equals("chrome") || !seleniumSettings.getCodeCoverage()) {
            return true;
        }

        List<String> tests = new ArrayList<>();
        tests.add("TasksEditTest");
        tests.add("TbSameFieldsOnDifferentTabsTest");
        tests.add("WorkflowWithKeyWithCurrentWithParentTest");
        tests.add("TbSortMultiTest");
        tests.add("TbPrivFieldsDrillDownParentApplet2Tab2Test");
        tests.add("TbPrivFieldsDrillDownApplet2Tab2Test");
        tests.add("TbCloningTest");
        tests.add("TbTemplatingTest");
        tests.add("TbRestrictedMyFilterUserAsDirectChildOfNotDirectParentTest");

        tests.add("TbGridEditorCurrentTest");
        tests.add("TbGridEditorChildTest");
        tests.add("TbGridEditorParentTest");
        tests.add("TbGridEditorWp1Test");
        tests.add("TbGridEditorWp2Test");
        tests.add("TbGridEditorDrillDownATest");
        tests.add("TbGridEditorDrillDownZTest");

        tests.add("TbGridRowEditorTrackorCurrentTest");
        tests.add("TbGridRowEditorTrackorChildTest");
        tests.add("TbGridRowEditorTrackorParentTest");
        tests.add("TbGridRowEditorTrackorWp1Test");
        tests.add("TbGridRowEditorTrackorWp2Test");
        tests.add("TbGridRowEditorTrackorDrillDownATest");
        tests.add("TbGridRowEditorTrackorDrillDownAParentTest");
        tests.add("TbGridRowEditorTrackorDrillDownZTest");
        tests.add("TbGridRowEditorTrackorDrillDownZParentTest");

        if (tests.contains(seleniumSettings.getTestName())) {
            seleniumLogger.error("Skip Code Coverage for test " + seleniumSettings.getTestName());
            return true;
        }

        return false;
    }

    private void sendWSMessage(String url, String message) throws IOException, WebSocketException, InterruptedException {
        Object object1 = waitCoordinator.get();
        String testName = seleniumSettings.getTestName();
        if (ws.get() == null) {
            WebSocket webSocket = new WebSocketFactory()
                    .createSocket(url)
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {
                            try {
                                if (new JSONObject(message).getInt("id") == 3) {
                                    JSONObject jsonObject = new JSONObject(message);
                                    removeScriptsWithoutName(jsonObject);
                                    //removeFunctionsWithoutName(jsonObject);
                                    //removeNotCoverageRanges(jsonObject);
                                    //removeEmptyFunctions(jsonObject);
                                    //removeEmptyScripts(jsonObject);
                                    try {
                                        Files.write(Paths.get("/opt/tomcat/guitest-scripts/code_coverage/coverage_v8/" + testName + ".json"), jsonObject.toString().getBytes());
                                    } catch (Exception e) {
                                        seleniumLogger.error(testName + " exception in writeCoverageFile " + e.getMessage());
                                    }
                                }
                            } catch (Exception e) {
                                seleniumLogger.error(testName + " exception in coverage onTextMessage response " + message);
                                seleniumLogger.error(testName + " exception in coverage onTextMessage " + e.getMessage());
                            } finally {
                                synchronized (object1) {
                                    object1.notifyAll();
                                }
                            }
                        }
                    })
                    .connect();
            ws.set(webSocket);
        }

        ws.get().sendText(message);

        synchronized (object1) {
            //object1.wait();
            object1.wait(300 * 1000);
        }
    }

    private void removeScriptsWithoutName(JSONObject jsonObject) {
        JSONArray scriptArray = jsonObject.getJSONObject("result").getJSONArray("result");
        ArrayList<Integer> scriptsToRemove = new ArrayList<Integer>();
        for (int i = 0; i < scriptArray.length(); i++) {
            JSONObject script = scriptArray.getJSONObject(i);
            if (script.getString("url").isEmpty()) {
                scriptsToRemove.add(i);
            } else if (!script.getString("url").contains("SNAPSHOT")) {
                scriptsToRemove.add(i);
            }
        }

        for (int i = scriptsToRemove.size() - 1; i >= 0; i--) {
            scriptArray.remove(scriptsToRemove.get(i));
        }
    }

    private void removeFunctionsWithoutName(JSONObject jsonObject) {
        JSONArray scriptArray = jsonObject.getJSONObject("result").getJSONArray("result");
        for (int i = 0; i < scriptArray.length(); i++) {
            //seleniumLogger.info("Files.write 3 1 scriptArray " + scriptArray.getJSONObject(i).getString("url"));
            JSONArray functionArray = scriptArray.getJSONObject(i).getJSONArray("functions");
            ArrayList<Integer> functionsToRemove = new ArrayList<Integer>();
            for (int j = 0; j < functionArray.length(); j++) {
                JSONObject function = functionArray.getJSONObject(j);
                if (function.getString("functionName").isEmpty()) {
                    functionsToRemove.add(j);
                }
            }

            for (int j = functionsToRemove.size() - 1; j >= 0; j--) {
                functionArray.remove(functionsToRemove.get(j));
            }
        }
    }

    private void removeNotCoverageRanges(JSONObject jsonObject) {
        JSONArray scriptArray = jsonObject.getJSONObject("result").getJSONArray("result");
        for (int i = 0; i < scriptArray.length(); i++) {
            JSONArray functionArray = scriptArray.getJSONObject(i).getJSONArray("functions");
            for (int j = 0; j < functionArray.length(); j++) {
                JSONArray rangeArray = functionArray.getJSONObject(j).getJSONArray("ranges");
                ArrayList<Integer> rangesToRemove = new ArrayList<Integer>();
                for (int k = 0; k < rangeArray.length(); k++) {
                    //seleniumLogger.info("Files.write 3 3 rangeArray");
                    JSONObject range = rangeArray.getJSONObject(k);
                    if (range.getInt("count") == 0) {
                        rangesToRemove.add(k);
                    }
                }

                for (int k = rangesToRemove.size() - 1; k >= 0; k--) {
                    rangeArray.remove(rangesToRemove.get(k));
                }
            }
        }
    }

    private void removeEmptyFunctions(JSONObject jsonObject) {
        JSONArray scriptArray = jsonObject.getJSONObject("result").getJSONArray("result");
        for (int i = 0; i < scriptArray.length(); i++) {
            JSONArray functionArray = scriptArray.getJSONObject(i).getJSONArray("functions");
            ArrayList<Integer> functionsToRemove = new ArrayList<Integer>();
            for (int j = 0; j < functionArray.length(); j++) {
                JSONArray rangeArray = functionArray.getJSONObject(j).getJSONArray("ranges");
                if (rangeArray.length() == 0) {
                    functionsToRemove.add(j);
                }
            }

            for (int j = functionsToRemove.size() - 1; j >= 0; j--) {
                functionArray.remove(functionsToRemove.get(j));
            }
        }
    }

    private void removeEmptyScripts(JSONObject jsonObject) {
        JSONArray scriptArray = jsonObject.getJSONObject("result").getJSONArray("result");
        ArrayList<Integer> scriptsToRemove = new ArrayList<Integer>();
        for (int i = 0; i < scriptArray.length(); i++) {
            JSONArray functionArray = scriptArray.getJSONObject(i).getJSONArray("functions");
            if (functionArray.length() == 0) {
                scriptsToRemove.add(i);
            }
        }

        for (int i = scriptsToRemove.size() - 1; i >= 0; i--) {
            scriptArray.remove(scriptsToRemove.get(i));
        }
    }

    private String getWebSocketDebuggerUrl(String host) {
        String webSocketDebuggerUrl = "";
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("http://" + host + ":9223/json");
            request.addHeader("accept", "application/json");
            HttpResponse response = httpClient.execute(request);
            JSONArray jsonArray = new JSONArray(extractObject(response));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("type").equals("page")) {
                    webSocketDebuggerUrl = jsonObject.getString("webSocketDebuggerUrl");
                    break;
                }
            }
        } catch (Exception e) {
            seleniumLogger.error("exception in getWebSocketDebuggerUrl " + e.getMessage());
        }

        if (webSocketDebuggerUrl.equals("")) {
            throw new SeleniumUnexpectedException("webSocketDebuggerUrl not found");
        }

        return webSocketDebuggerUrl;
    }

    private String getHost(String hostName, SessionId session) {
        String host = null;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("http://" + hostName + ":5555/grid/api/testsession?session=" + session);
            request.addHeader("accept", "application/json");
            HttpResponse response = httpClient.execute(request);
            JSONObject object = new JSONObject(extractObject(response));
            URL myURL = new URL(object.getString("proxyId"));
            if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
                host = myURL.getHost();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return host;
    }

    private String extractObject(HttpResponse resp) throws IOException, JSONException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
        StringBuffer s = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            s.append(line);
        }
        rd.close();
        return s.toString();
    }

}