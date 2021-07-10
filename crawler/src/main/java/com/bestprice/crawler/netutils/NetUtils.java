package com.bestprice.crawler.netutils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NetUtils<T> {

    URL url = null;
    Class c = null;

    public NetUtils(String urlString, Class<T> c) {
        try {
            this.url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.c = c;
    }

    public T getObject() {
        HttpURLConnection connection = null;
        T obj = null;
        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            obj = (T) (new ObjectMapper()).readValue(responseStream, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void sendObject2(T object) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Accept", "application/json");
            OutputStream os = connection.getOutputStream();
            byte[] input = (new ObjectMapper()).writeValueAsString(object).getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(T object) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(this.url.toURI())
                    .POST(HttpRequest.BodyPublishers.ofString((new ObjectMapper()).writeValueAsString(object)))
                    .header("Content-Type", "application/json")
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Failed to send item: " + object);
        }
    }
}
