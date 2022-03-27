package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("javax.net.ssl.keyStore", "certs/vasya.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");
        System.setProperty("javax.net.ssl.trustStore", "certs/truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "passphrase");

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder(URI.create("https://127.0.0.1:8443/users.getAll"))
                .GET()
                .build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        System.out.println("response = " + response.body());
    }
}
