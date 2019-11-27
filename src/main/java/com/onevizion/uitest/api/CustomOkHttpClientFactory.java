package com.onevizion.uitest.api;

import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.okhttp.OkHttpClient;
import java.util.Objects;
import java.net.URL;
import java.time.Duration;

public class CustomOkHttpClientFactory extends OkHttpClient.Factory {

    @Override
    public HttpClient createClient(URL url) {
        Objects.requireNonNull(url, "URL to use as base URL must be set.");

        ClientConfig clientConfig = ClientConfig.defaultConfig().baseUrl(url).readTimeout(Duration.ofMinutes(30));

        return createClient(clientConfig);
    }

}