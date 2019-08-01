package com.onevizion.uitest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
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
public class CodeCoverage {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    private String wsUrl;

    private WebSocket ws;

    private Object waitCoordinator = new Object();

    private String coverage;

    public void start() {
        if (!seleniumSettings.getBrowser().equals("chrome") || !seleniumSettings.getCodeCoverage()) {
            return;
        }

        SessionId sessionId = ((RemoteWebDriver) seleniumSettings.getWebDriver()).getSessionId();
        String[] hostAndPort = getHostNameAndPort(seleniumSettings.getRemoteAddress(), 5555, sessionId);
        seleniumLogger.info(hostAndPort[0]);
        seleniumLogger.info(hostAndPort[1]);
        wsUrl = getWebSocketDebuggerUrl(hostAndPort[0]);
        seleniumLogger.info(wsUrl);

        try {
            sendWSMessage(wsUrl, "{\"id\":1, \"method\":\"Profiler.enable\"}");
        } catch (IOException | WebSocketException | InterruptedException e) {
            seleniumLogger.error("exception in coverageStart " + e.getMessage());
        }

        try {
            sendWSMessage(wsUrl, "{\"id\":2, \"method\":\"Profiler.startPreciseCoverage\", \"params\":{\"callCount\":true, \"detailed\":true}}");
        } catch (IOException | WebSocketException | InterruptedException e) {
            seleniumLogger.error("exception in coverageStart " + e.getMessage());
        }
    }

    public void finish() {
        if (!seleniumSettings.getBrowser().equals("chrome") || !seleniumSettings.getCodeCoverage()) {
            return;
        }

        try {
            sendWSMessage(wsUrl, "{\"id\":3, \"method\":\"Profiler.takePreciseCoverage\"}");
        } catch (IOException | WebSocketException | InterruptedException e) {
            seleniumLogger.error("exception in coverageFinish " + e.getMessage());
        }

        try {
            sendWSMessage(wsUrl, "{\"id\":4, \"method\":\"Profiler.stopPreciseCoverage\"}");
        } catch (IOException | WebSocketException | InterruptedException e) {
            seleniumLogger.error("exception in coverageFinish " + e.getMessage());
        }

        try {
            //Files.deleteIfExists(Paths.get(seleniumSettings.getTestName() + ".json"));
            Files.write(Paths.get("/opt/tomcat/guitest-scripts/code_coverage/" + seleniumSettings.getTestName() + ".json"), coverage.getBytes());
        } catch (IOException e) {
            seleniumLogger.error("exception in coverageFinish " + e.getMessage());
        }
    }

    private String getWebSocketDebuggerUrl(String host) {
        String webSocketDebuggerUrl = "";

        try {
            URL url = new URL("http://" + host + ":9222/json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String json = org.apache.commons.io.IOUtils.toString(reader);
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("type").equals("page")) {
                    webSocketDebuggerUrl = jsonObject.getString("webSocketDebuggerUrl");
                    break;
                }
            }
        } catch (IOException e) {
            seleniumLogger.error("exception in getWebSocketDebuggerUrl " + e.getMessage());
        }

        if (webSocketDebuggerUrl.equals("")) {
            throw new SeleniumUnexpectedException("webSocketDebuggerUrl not found");
        }

        return webSocketDebuggerUrl;
    }

    private void sendWSMessage(String url, String message) throws IOException, WebSocketException, InterruptedException {
        if (ws==null) {
            ws = new WebSocketFactory()
                    .createSocket(url)
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {
                            if (new JSONObject(message).getInt("id") == 3){
                                //try {
                                    //seleniumLogger.info("Files.write 1");
                                    //Files.deleteIfExists(Paths.get("name_full.txt"));
                                    //Files.write(Paths.get("name_full.txt"), message.getBytes());
                                    //seleniumLogger.info("Files.write 2");

                                    //seleniumLogger.info("Files.write 3");
                                    JSONObject jsonObject = new JSONObject(message);

                                    removeScriptsWithoutName(jsonObject);
                                    //removeFunctionsWithoutName(jsonObject);
                                    //removeNotCoverageRanges(jsonObject);
                                    //removeEmptyFunctions(jsonObject);
                                    //removeEmptyScripts(jsonObject);
                                    //seleniumLogger.info("Files.write 4");

                                    //seleniumLogger.info("Files.write 5");
                                    //Files.deleteIfExists(Paths.get("name_small.txt"));
                                    //Files.write(Paths.get("name_small.txt"), jsonObject.toString().getBytes());
                                    //seleniumLogger.info("Files.write 6");
                                    coverage = jsonObject.toString();
                                //} catch (IOException e) {
                                //    seleniumLogger.error("exception in Files.write " + e.getMessage());
                                //}
                            } else {
                                //seleniumLogger.info("gkovalev " + message);
                            }
                            synchronized (waitCoordinator) {
                                waitCoordinator.notifyAll();
                            }
                        }
                    })
                    .connect();
        }
        ws.sendText(message);
        synchronized (waitCoordinator) {
            waitCoordinator.wait();
        }
        //seleniumLogger.info("gkovalev GOOD");
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

    public static String[] getHostNameAndPort(String hostName, int port, SessionId session) {
        String[] hostAndPort = new String[2];
        String errorMsg = "Failed to acquire remote webdriver node and port info. Root cause: ";

        try {
            HttpHost host = new HttpHost(hostName, port);
            DefaultHttpClient client = new DefaultHttpClient();
            URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session=" + session);
            BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST", sessionURL.toExternalForm());
            HttpResponse response = client.execute(host, r);
            JSONObject object = extractObject(response);
            URL myURL = new URL(object.getString("proxyId"));
            if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
                hostAndPort[0] = myURL.getHost();
                hostAndPort[1] = Integer.toString(myURL.getPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(errorMsg, e);
        }
        return hostAndPort;
    }

    private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
        StringBuffer s = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            s.append(line);
        }
        rd.close();
        JSONObject objToReturn = new JSONObject(s.toString());
        return objToReturn;
    }

}