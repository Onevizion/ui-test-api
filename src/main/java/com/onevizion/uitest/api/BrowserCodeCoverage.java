package com.onevizion.uitest.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

@Component
public class BrowserCodeCoverage {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    @Resource
    private SeleniumNode seleniumNode;

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
        wsUrl.remove();

        wsUrl.set(seleniumNode.getWebSocketDebuggerUrl());

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
            object1.wait(300L * 1000L);
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

}