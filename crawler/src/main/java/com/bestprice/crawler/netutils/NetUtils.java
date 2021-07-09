package com.bestprice.crawler.netutils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
}
