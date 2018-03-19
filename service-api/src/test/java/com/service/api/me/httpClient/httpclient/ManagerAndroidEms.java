package com.service.api.me.httpClient.httpclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
 * 便民管理端快递部分接口调试
 */
public class ManagerAndroidEms {

    /**
     * 访问地址
     */


    /**
     * getResponseBody  result String
     */
    private static void getResponseBody( CloseableHttpClient httpclient, HttpPost httppost ) throws IOException {
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

                System.out.println(out.toString());

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
     * 更新图片
     * @throws Exception
     */
    private static void  saveSignPic() throws Exception{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187+"/manager-android/ems/saveSignPic");
            final FileBody bin = new FileBody(new File("/home/ubuntu/Pictures/2000.jpg"), ContentType.IMAGE_JPEG);
            final StringBody ids = new StringBody("39737,1371", ContentType.TEXT_PLAIN);
            final StringBody adminId = new StringBody("1", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    .addPart("ids", ids)
                    .addPart("adminId", adminId)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }

    /**
     * 快递查询
     * @throws Exception
     */
    private static void selectRecordByMobile() throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187 + "/manager-android/ems/selectRecordByMobile");
            final StringBody houseId = new StringBody("8", ContentType.TEXT_PLAIN);
            final StringBody mobile = new StringBody("9995", ContentType.TEXT_PLAIN);
            final StringBody type = new StringBody("0", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("mobile", mobile)
                    .addPart("houseId", houseId)
                    .addPart("type", type)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }

    /**
     * 更新状态(站点配送)
     * @throws Exception
     */
    private static void updateState() throws Exception{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187 + "/manager-android/ems/updateState");
            final StringBody ids = new StringBody("1370", ContentType.TEXT_PLAIN);
            final StringBody adminId = new StringBody("1", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("ids", ids)
                    .addPart("adminId", adminId)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }

    /**
     * 快递记录查询(所有的收快递数据)
     * @throws Exception
     */
    public static void selectRecord() throws Exception{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187 + "/manager-android/ems/selectRecord");
            final StringBody houseId = new StringBody("5", ContentType.TEXT_PLAIN);
            final StringBody deliveryType = new StringBody("2", ContentType.TEXT_PLAIN);
            final StringBody communicate = new StringBody("-1", ContentType.TEXT_PLAIN);
            final StringBody state = new StringBody("-1", ContentType.TEXT_PLAIN);
            final StringBody mobile = new StringBody("9995", ContentType.TEXT_PLAIN);
            final StringBody which = new StringBody("1", ContentType.TEXT_PLAIN);
            final StringBody lastId = new StringBody("0", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("houseId", houseId)
                    .addPart("deliveryType", deliveryType)
                    .addPart("communicate", communicate)
                    .addPart("state", state)
//                    .addPart("mobile", mobile)
                    .addPart("which", which)
                    .addPart("lastId", lastId)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }

    /**
     * 按手机号模糊查询用户信息
     * @throws Exception
     */
    public static void selectByMobile() throws Exception{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187 + "/manager-android/ems/selectByMobile");
            final StringBody houseId = new StringBody("8", ContentType.TEXT_PLAIN);
            final StringBody mobile = new StringBody("1", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("houseId", houseId)
                    .addPart("mobile", mobile)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }


    /**
     * 登录接口
     */
    public static void login() throws Exception{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187 + "/manager-android/hlhouseadmin/loginv103");
            final StringBody passwd = new StringBody("e10adc3949ba59abbe56e057f20f883e", ContentType.TEXT_PLAIN);
            final StringBody mobile = new StringBody("13366100567", ContentType.TEXT_PLAIN);
            final StringBody machineCode = new StringBody("15534", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("passwd", passwd)
                    .addPart("mobile", mobile)
                    .addPart("machineCode", machineCode)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }


    /**保存收快递**/
    public static void saveSearch200() throws Exception{
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                final HttpPost httppost = new HttpPost(ipAddress_187 + "/manager-android/ems/saveSearch200");
                final StringBody bill = new StringBody("987435445668", ContentType.TEXT_PLAIN);
                final StringBody comName = new StringBody("申通", ContentType.TEXT_PLAIN);
                final StringBody comNumber = new StringBody("sto", ContentType.TEXT_PLAIN);
                final StringBody mobile = new StringBody("13366100567", ContentType.TEXT_PLAIN);
                final StringBody recipients = new StringBody("张三", ContentType.TEXT_PLAIN);
                final StringBody deliveryType = new StringBody("0", ContentType.TEXT_PLAIN);
                final StringBody packType = new StringBody("1", ContentType.TEXT_PLAIN);
                final StringBody adminId = new StringBody("9", ContentType.TEXT_PLAIN);
                final StringBody houseId = new StringBody("5", ContentType.TEXT_PLAIN);
                final StringBody substationId = new StringBody("445", ContentType.TEXT_PLAIN);

                final HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("bill", bill)
                        .addPart("comName", comName)
                        .addPart("comNumber", comNumber)
                        .addPart("mobile", mobile)
                        .addPart("recipients", recipients)
                        .addPart("deliveryType", deliveryType)
                        .addPart("packType", packType)
                        .addPart("adminId", adminId)
                        .addPart("houseId", houseId)
                        .addPart("substationId", substationId)
                        .build();
                httppost.setEntity(reqEntity);
                System.out.println("executing request " + httppost);
                getResponseBody(httpclient,httppost);
            }
    }


    /**查询详情**/
    public static void selectDetail() throws Exception{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress_187 + "/manager-android/ems/selectRecordById");

            final StringBody houseId = new StringBody("6", ContentType.TEXT_PLAIN);
            final StringBody id = new StringBody("545369", ContentType.TEXT_PLAIN);

            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("houseId", houseId)
                    .addPart("id", id)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost);
            getResponseBody(httpclient,httppost);
        }
    }

    private  final static String ipAddress_187 = "http://192.168.88.138:8070";
//      private final static String ipAddress_187 = "http://192.168.100.187:8282";

    public static void main(final String[] args) throws Exception {
//          saveSignPic(); /**更新图片**/
//          selectRecordByMobile();/**查询签收的快递**/
//          updateState();/**更新状态(站点配送)**/
//          selectRecord();/**查询所有的快递**/
          selectByMobile();/**按手机号模糊查询用户信息**/
//          login();/**登录接口**/
//          saveSearch200();/**保存收快递**/
//          selectDetail();/**查询详情接口**/
    }
}
