package com.service.api.me.httpClient.httpclient;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 便民管理端回收部分接口调试
 */
public class ManagerAndroidRecycle {

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
                System.out.println(   out.toString()  );
                System.out.println( resEntity.getContent() );
                System.out.println("Response content length: " + resEntity.getContentLength());
            }
            EntityUtils.consume(resEntity);
        }
    }

    /**
     * 根据服务亭id获取回收类型
     * **/
    private static void getRecycleTypeByHouseId() throws Exception{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress+"/manager-android/ems/saveSignPic");
            final FileBody bin = new FileBody(new File("/home/ubuntu/Pictures/2000.jpg"), ContentType.IMAGE_JPEG);
            final StringBody comment = new StringBody("39737,1371", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    .addPart("ids", comment)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }

    /**ip请求地址**/
    private final static String ipAddress="";
//    private final static String ipAddress="";

    public static void main( String sck[] ){
        try {



            getRecycleTypeByHouseId();/**根据服务亭id获取回收类型**/

        }catch( Exception exp){
            exp.printStackTrace();
        }
    }
}
