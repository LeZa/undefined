package com.service.api.me.battery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.Charsets;
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

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BatteryTest {

    private final static String oauthAddress="http://oauth:oauth@localhost:9070/oauth/token"; //oauth
    private final static String ipAddress="http://192.168.89.54:9154/battery";

    /**
     * 获取token
     */
    private Map<String,Object> login() throws Exception{
        HashMap<String,Object> loginMap = new HashMap<String,Object>();
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(oauthAddress);
            final StringBody grant_type = new StringBody("password", ContentType.TEXT_PLAIN);
            final StringBody username = new StringBody("admin", ContentType.TEXT_PLAIN);
            final StringBody password = new StringBody("admin", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("grant_type", grant_type)
                    .addPart("username", username)
                    .addPart("password", password)
                    .build();
            httppost.setEntity(reqEntity);
            JsonObject jsonObject = getPOSTResponseJsonObejct(httpclient,httppost);
            charOutStream( new Gson().toJson( jsonObject  ));
            String userToken = jsonObject.get("access_token").getAsString();
            loginMap.put("userToken",userToken);
            return loginMap;
        }
    }

    /**
     * getResponseBody  result String
     */
    private static JsonObject getPOSTResponseJsonObejct( CloseableHttpClient httpclient, HttpPost httppost ) throws IOException {
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
    private static JsonObject getGETResponseJsonObejct( CloseableHttpClient httpclient, HttpGet httppost ) throws IOException {
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


    @Test
    public void selectByUid() throws Exception{

        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress);
        final StringBody name = new StringBody("2170054762", ContentType.TEXT_PLAIN);
        final StringBody CODE = new StringBody("HQ", ContentType.TEXT_PLAIN);
       final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("SN", name)
                .addPart("CODE",CODE)
                .build();
        httpPost.setEntity( reqEntity );
        getPOSTResponseJsonObejct(httpclient,httpPost);

//                getGETResponseJsonObejct(httpclient,httpGet);

    }


    /**
     * @Description  预约上门回收
     */
    @Test
    public void save() throws Exception {
        Map<String,Object> loginMap = login();
        System.out.println(loginMap.get("userToken"));
        String token = String.valueOf(loginMap.get("userToken"));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress+"/appointment/save");

        final StringBody wechatOfficialAccountsId = new StringBody("23255", ContentType.TEXT_PLAIN);
        final StringBody name = new StringBody("苏世乐", ContentType.create("text/plain","UTF-8"));
        final StringBody mobile = new StringBody("13366100567", ContentType.TEXT_PLAIN);
        final StringBody provinceId = new StringBody("1", ContentType.TEXT_PLAIN);
        final StringBody cityId = new StringBody("2", ContentType.TEXT_PLAIN);
        final StringBody countyId = new StringBody("3", ContentType.TEXT_PLAIN);
        final StringBody areaId = new StringBody("4", ContentType.TEXT_PLAIN);
        final StringBody houseId = new StringBody("6", ContentType.TEXT_PLAIN);
        final StringBody startTime = new StringBody("2018-03-13 12:26:30", ContentType.TEXT_PLAIN);
        final StringBody endTime = new StringBody("2018-03-13 14:26:30", ContentType.TEXT_PLAIN);
        final StringBody address = new StringBody("河北大道",ContentType.create("text/plain","UTF-8"));
        final StringBody type = new StringBody("衣物,其他", ContentType.create("text/plain","UTF-8"));
        final StringBody access_token = new StringBody(token, ContentType.TEXT_PLAIN);
        final HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("name", name)
                .addPart("mobile", mobile)
                .addPart("provinceId", provinceId)
                .addPart("cityId", cityId)
                .addPart("countyId", countyId)
                .addPart("areaId", areaId)
                .addPart("houseId", houseId)
                .addPart("startTime", startTime)
                .addPart("endTime", endTime)
                .addPart("address", address)
                .addPart("type", type)
                .addPart("wechatOfficialAccountsId", wechatOfficialAccountsId)
                .addPart("access_token",access_token)
                .build();
        httpPost.setEntity( httpEntity );
        charOutStream( new Gson().toJson( getPOSTResponseJsonObejct(httpclient,httpPost)));
    }


    /**
     * @Description 月投放量和乐豆
     */
    @Test
    public void monthVal() throws Exception {
//        Map<String,Object> loginMap = login();
//        System.out.println(loginMap.get("userToken"));
//        String token = String.valueOf(loginMap.get("userToken"));
        String token = "ee77e208-e093-45ab-825d-68de4685f80b";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress+"/appointment/monthVal");
        final StringBody mobile = new StringBody("13366100567", ContentType.TEXT_PLAIN);
        final StringBody wechatOfficialAccountsId = new StringBody("1234567890", ContentType.TEXT_PLAIN);
        final StringBody access_token = new StringBody(token, ContentType.TEXT_PLAIN);
        final HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("mobile", mobile)
                .addPart("wechatOfficialAccountsId", wechatOfficialAccountsId)
//                .addPart("access_token",access_token)
                .build();
        httpPost.setEntity( httpEntity );
        charOutStream( new Gson().toJson(getPOSTResponseJsonObejct(httpclient,httpPost) ));
    }

    /**
     * @Description 上门回收记录
     * @throws Exception
     */
    @Test
    public void homeRecy() throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress+"/appointment/homeRecy");
        final StringBody mobile = new StringBody("18810844169", ContentType.TEXT_PLAIN);
        final StringBody wechatOfficialAccountsId = new StringBody("1234567890", ContentType.TEXT_PLAIN);
        final StringBody currentPage = new StringBody("6", ContentType.TEXT_PLAIN);
        final StringBody pageSize = new StringBody("150", ContentType.TEXT_PLAIN);
        final HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("mobile", mobile)
                .addPart("wechatOfficialAccountsId", wechatOfficialAccountsId)
                .addPart("currentPage", currentPage)
                .addPart("pageSize", pageSize)
                .build();
        httpPost.setEntity( httpEntity );
        charOutStream(  new Gson().toJson( getPOSTResponseJsonObejct(httpclient,httpPost) ));
    }


    /**
     * @Description  智能回收记录
     * @throws Exception
     */
    @Test
    public void machineRecy() throws Exception{

        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress+"/appointment/machineRecy");
        final StringBody mobile = new StringBody("15855271830", ContentType.TEXT_PLAIN);
        final StringBody wechatOfficialAccountsId = new StringBody("1234567890", ContentType.TEXT_PLAIN);
        final StringBody currentPage = new StringBody("1", ContentType.TEXT_PLAIN);
        final StringBody pageSize = new StringBody("5", ContentType.TEXT_PLAIN);
        final HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("mobile", mobile)
                .addPart("wechatOfficialAccountsId", wechatOfficialAccountsId)
                .addPart("currentPage", currentPage)
                .addPart("pageSize", pageSize)
                .build();
        httpPost.setEntity( httpEntity );
        charOutStream(  new Gson().toJson( getPOSTResponseJsonObejct(httpclient,httpPost) ));
    }


    /**
     * @Description  上门回收详情
     */
    @Test
    public void homeRecyDetai() throws Exception {
        Map<String,Object> loginMap = login();
        System.out.println(loginMap.get("userToken"));
        String token = String.valueOf(loginMap.get("userToken"));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress+"/appointment/homeRecyDetai");
        final StringBody wechatOfficialAccountsId = new StringBody("23255", ContentType.TEXT_PLAIN);
        final StringBody id = new StringBody("1413", ContentType.TEXT_PLAIN);
        final StringBody access_token = new StringBody(token, ContentType.TEXT_PLAIN);
        final HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("wechatOfficialAccountsId", wechatOfficialAccountsId)
                .addPart("id", id)
                .addPart("access_token",access_token)
                .build();
        httpPost.setEntity( httpEntity );
       charOutStream( new Gson().toJson( getPOSTResponseJsonObejct(httpclient,httpPost)));
    }

    /**
     * 回收价格     * @throws Exception
     */
    @Test
    public void searchRecyPrice() throws  Exception{
        Map<String,Object> loginMap = login();
        System.out.println(loginMap.get("userToken"));
        String token = String.valueOf(loginMap.get("userToken"));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress+"/recyPrice/search");
        final StringBody wechatOfficialAccountsId = new StringBody("23255", ContentType.TEXT_PLAIN);
        final StringBody currentPage = new StringBody("1", ContentType.TEXT_PLAIN);
        final StringBody pageSize = new StringBody("5", ContentType.TEXT_PLAIN);
        final StringBody access_token = new StringBody(token, ContentType.TEXT_PLAIN);
        final HttpEntity httpEntity = MultipartEntityBuilder.create().setCharset(Charsets.UTF_8)
                .addPart("wechatOfficialAccountsId", wechatOfficialAccountsId)
                .addPart("currentPage", currentPage)
                .addPart("pageSize", pageSize)
//                .addPart("word", word)
                .addPart("access_token",access_token)
                .build();
        httpPost.setEntity( httpEntity );
        charOutStream(  new Gson().toJson( getPOSTResponseJsonObejct(httpclient,httpPost) ));
    }

    /**
     * @Description 调整回收价格
     */
    @Test
    public void recyEdit() throws Exception{
        Map<String,Object> loginMap = login();
        System.out.println(loginMap.get("userToken"));
        String token = String.valueOf(loginMap.get("userToken"));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress+"/recyPrice/edit");
        final StringBody wechatOfficialAccountsId = new StringBody("23255", ContentType.TEXT_PLAIN);
        final StringBody id = new StringBody("1", ContentType.TEXT_PLAIN);
        final StringBody price = new StringBody("10",ContentType.TEXT_PLAIN);
        final StringBody access_token = new StringBody(token, ContentType.TEXT_PLAIN);
        final HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("wechatOfficialAccountsId", wechatOfficialAccountsId)
                .addPart("price", price)
                .addPart("id", id)
                .addPart("access_token",access_token)
                .build();
        httpPost.setEntity( httpEntity );
        charOutStream( new  Gson().toJson( getPOSTResponseJsonObejct(httpclient,httpPost)));
    }

    /**
     * @Description  上门回收列表
     * @throws Exception
     */
    @Test
    public void homeRecyPhp() throws  Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(ipAddress+"/appointment/homeRecyPhp");
        final StringBody wechatOfficialAccountsId = new StringBody("1234567890", ContentType.TEXT_PLAIN);
        final StringBody currentPage = new StringBody("2", ContentType.TEXT_PLAIN);
        final StringBody pageSize = new StringBody("5",ContentType.TEXT_PLAIN);
        final HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("wechatOfficialAccountsId", wechatOfficialAccountsId)
                .addPart("currentPage", currentPage)
                .addPart("pageSize", pageSize)
                .build();
        httpPost.setEntity( httpEntity );
         charOutStream( new Gson().toJson(getPOSTResponseJsonObejct(httpclient,httpPost)));
    }

    public static void charOutStream(String str) throws Exception{
        File file = new File("/home/cent/test.txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Writer out = new FileWriter(file);
        out.write(str);
        out.close();

    }
}
