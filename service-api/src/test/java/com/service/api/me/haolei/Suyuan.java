package com.service.api.me.haolei;

import org.apache.hc.client5.http.classic.methods.HttpPost;

import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.junit.Test;


public class Suyuan {


    private  final static String ipAddress_187 = "http://192.168.100.136:8001";
//      private final static String ipAddress_187 = "http://192.168.100.187:8282";

    /**
     * kucunchaxun
     * @throws Exception
     */
    @Test
    public  void kucunchaxun() throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187 + "/api/stock/rt-total-count");
            final StringBody source_os = new StringBody("6", ContentType.TEXT_PLAIN);
            final StringBody category_id = new StringBody("113", ContentType.TEXT_PLAIN);//分类id
            final StringBody warehouse_id = new StringBody("", ContentType.TEXT_PLAIN);//服务亭id
            final StringBody substation_id = new StringBody("21", ContentType.TEXT_PLAIN);//分站id

            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("source_os", source_os)
                    .addPart("category_idd", category_id)
                    .addPart("warehouse_id", warehouse_id)
                    .addPart("substation_id",substation_id)
                    .build();
            httppost.addHeader("token","9233fd33ea929edb7f95c99cdbe5e7d1");
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            ManagerAndroidEms.getResponseBody(httpclient,httppost);
        }
    }

}
