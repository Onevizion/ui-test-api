package com.onevizion.uitest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class SeleniumNode {

    @Autowired
    private SeleniumSettings seleniumSettings;

    public String getNodeHostAndPort() {
        String[] hostAndPort = getNode();
        return hostAndPort[0] + ":" + hostAndPort[1];
    }

    public String getWebSocketDebuggerUrl() {
        String nodeHost = getNode()[0];
        String webSocketDebuggerUrl = "";

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("http://" + nodeHost + ":9223/json");
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
            throw new SeleniumUnexpectedException("exception in SeleniumNode.getWebSocketDebuggerUrl", e);
        }

        return webSocketDebuggerUrl;
    }

    private String[] getNode() {
        SessionId sessionId = ((RemoteWebDriver) seleniumSettings.getWebDriver()).getSessionId();

        String[] hostAndPort = new String[2];

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("http://" + seleniumSettings.getRemoteAddress() + ":5555/grid/api/testsession?session=" + sessionId);
            request.addHeader("accept", "application/json");
            HttpResponse response = httpClient.execute(request);
            JSONObject object = new JSONObject(extractObject(response));
            URL myURL = new URL(object.getString("proxyId"));
            if (myURL.getHost() != null && myURL.getPort() != -1) {
                hostAndPort[0] = myURL.getHost();
                hostAndPort[1] = Integer.toString(myURL.getPort());
            }
        } catch (Exception e) {
            throw new SeleniumUnexpectedException("exception in SeleniumNode.getNode", e);
        }

        return hostAndPort;
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