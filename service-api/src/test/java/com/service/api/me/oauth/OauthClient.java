package com.service.api.me.oauth;

import com.build.pure.java.util.ConversionMd5;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class OauthClient {


    /**
     * getResponseBody  result String
     */
    private static JsonObject getResponseJsonObejct( CloseableHttpClient httpclient, HttpPost httppost ) throws IOException {
        try (CloseableHttpResponse response = httpclient.execute(httppost)) {
            System.out.println("----------------------------------------");
            final HttpEntity resEntity = response.getEntity();
            InputStream is = resEntity.getContent();
            StringBuilder out = new StringBuilder(1024);
            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }

            JsonObject returnData = new JsonParser().parse(out.toString()).getAsJsonObject();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            System.out.println( gson.toJson( returnData ));
            return returnData;
        }
    }


    /**
     * getResponseBody  result String
     */
    private static JsonObject getResponseJsonObejctHttpGet( CloseableHttpClient httpclient, HttpGet httpGet ) throws IOException {
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            System.out.println("----------------------------------------");
            final HttpEntity resEntity = response.getEntity();
            InputStream is = resEntity.getContent();
            StringBuilder out = new StringBuilder(1024);
            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }

            JsonObject returnData = new JsonParser().parse(out.toString()).getAsJsonObject();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            System.out.println( gson.toJson( returnData ));
            return returnData;
        }
    }


    @Test
    public void getAccessToken() throws Exception {
            CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet =  new HttpGet("http://client:secret@localhost:8080/oauth/token");
//                final HttpPost httppost = new HttpPost("http://client:secret@localhost:8080/oauth/token");
        httpGet.setHeader("Content-Type","application/x-www-form-urlencoded");
                final StringBody grant_type = new StringBody("authorization_code", ContentType.TEXT_PLAIN);
                final StringBody code = new StringBody("Fs9cst", ContentType.TEXT_PLAIN);
//                final StringBody redirect_uri = new StringBody("http://www.baidu.com", ContentType.TEXT_PLAIN);
                final HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("grant_type", grant_type)
                        .addPart("code", code)
//                        .addPart("redirect_uri", redirect_uri)
                        .build();
        httpGet.setEntity(reqEntity);
                JsonObject jsonObject = getResponseJsonObejctHttpGet(httpclient,httpGet);
                JsonObject dataJsonObejct = jsonObject.getAsJsonObject("data");

    }

    @Test
    public void getAccessTokenPost() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httppost = new HttpPost("http://android:android@localhost:7070/uaa/oauth/token");

        final StringBody grant_type = new StringBody("password", ContentType.TEXT_PLAIN);
        final StringBody password = new StringBody("admin", ContentType.TEXT_PLAIN);
        final StringBody username = new StringBody("admin", ContentType.TEXT_PLAIN);
        final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("grant_type", grant_type)
                .addPart("password", password)
                .addPart("username", username)
                .build();
        httppost.setEntity(reqEntity);
        JsonObject jsonObject = getResponseJsonObejct(httpclient,httppost);
        JsonObject dataJsonObejct = jsonObject.getAsJsonObject("data");

    }

}
