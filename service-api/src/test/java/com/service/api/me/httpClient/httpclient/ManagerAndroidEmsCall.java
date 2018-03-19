package com.service.api.me.httpClient.httpclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

public class ManagerAndroidEmsCall {





    /**
     * getResponseBody  result String
     */
    private static void getResponseBody(CloseableHttpClient httpclient, HttpPost httppost ) throws IOException {
        try (CloseableHttpResponse response = httpclient.execute(httppost)) {
            System.out.println("----------------------------------------");
            System.out.println(response);
            final HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                InputStream is = resEntity.getContent();
                StringBuffer out = new StringBuffer();
                byte[] b = new byte[4096];
                for (int n; (n = is.read(b)) != -1;) {
                    out.append(new String(b, 0, n));
                }

                JsonObject returnData = new JsonParser().parse(out.toString()).getAsJsonObject();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                System.out.println(gson.toJson(returnData));
                System.out.println( resEntity.getContent() );
                System.out.println("Response content length: " + resEntity.getContentLength());
            }
            EntityUtils.consume(resEntity);
        }
    }

    /**
     * 根据ID查询详情
     * @throws Exception
     */
    private static void  selectById() throws Exception{
        /**
         * 访问地址
         */

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187+"/manager-android/ems/selectById");
//            final FileBody bin = new FileBody(new File("/home/ubuntu/Pictures/2000.jpg"), ContentType.IMAGE_JPEG);
            final StringBody id = new StringBody("47180", ContentType.TEXT_PLAIN);
//            final StringBody adminId = new StringBody("1", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
//                    .addPart("file", bin)
                    .addPart("id", id)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }

//    private final static String ipAddress_187 = "http://localhost:8070";
private final static  String ipAddress_187 = "http://192.168.100.187:8282";

    public static void main(final String[] args) throws Exception {
        selectById();/**根据ID查询详情**/
    }

}
