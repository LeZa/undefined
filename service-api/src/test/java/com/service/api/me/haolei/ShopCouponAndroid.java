package com.service.api.me.haolei;

import com.build.config.ConversionMd5;
import com.google.gson.*;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ShopCouponAndroid {

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

    private final static String ipAddress="http://192.168.89.11:8070/";
//    private final static String ipAddress="http://192.168.100.187:8181";


    /**
     * 登录请求
     */
    private Map<String,Object> login() throws Exception{
        HashMap<String,Object> loginMap = new HashMap<String,Object>();
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(ipAddress+"/android/login/loginv260");
            final StringBody mobile = new StringBody("13366100567", ContentType.TEXT_PLAIN);
            final StringBody password = new StringBody(ConversionMd5.toMd5("123456"), ContentType.TEXT_PLAIN);
            final StringBody machineCode = new StringBody("123456", ContentType.TEXT_PLAIN);
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("mobile", mobile)
                    .addPart("password", password)
                    .addPart("machineCode", machineCode)
                    .build();
            httppost.setEntity(reqEntity);
            JsonObject jsonObject = getResponseJsonObejct(httpclient,httppost);
            JsonObject dataJsonObejct = jsonObject.getAsJsonObject("data");
            String userToken = dataJsonObejct.get("userToken").getAsString();
            loginMap.put("userToken",userToken);
            loginMap.put("substationId",445);
            JsonObject userJsonObject = dataJsonObejct.getAsJsonObject("user");
            String userId = userJsonObject.get("id").getAsString();
            loginMap.put("userId",userId);
            String timestamp = userJsonObject.get("ltime").getAsString();
            loginMap.put("timestamp",timestamp);
            return loginMap;
        }
    }

    /**
     * 根据用户ID获取用户优惠券
     * uid 用户id
     */
    @Test
    public void selectByUid() throws Exception{
        Map<String,Object> loginMap = login();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httppost = new HttpPost(ipAddress+"/android/shopcoupon/selectByUid");
        final StringBody lastId = new StringBody("0", ContentType.TEXT_PLAIN);
        final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("lastId", lastId)
                .build();
        httppost.addHeader("userToken",loginMap.get("userToken"));
        httppost.addHeader("userId",loginMap.get("userId"));
        httppost.addHeader("timestamp",loginMap.get("timestamp"));
        httppost.addHeader("substationId",loginMap.get("substationId"));
        httppost.addHeader("version",loginMap.get("3.0"));
        httppost.setEntity(reqEntity);
       getResponseJsonObejct(httpclient,httppost);
    }

    /**
     * 领取优惠券
     * uid:用户id
     * couponId:优惠券方案id
     * type:0代金券1折扣券
     */
    @Test
    public void saveByUid() throws Exception{
        Map<String,Object> loginMap = login();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httppost = new HttpPost(ipAddress+"/android/shopcoupon/saveByUid");
        final StringBody uid = new StringBody("5881", ContentType.TEXT_PLAIN);
        final StringBody couponId = new StringBody("44", ContentType.TEXT_PLAIN);
        final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("uid", uid)
                .addPart("couponId", couponId)
                .build();
        httppost.addHeader("userToken",loginMap.get("userToken"));
        httppost.addHeader("userId",loginMap.get("userId"));
        httppost.addHeader("timestamp",loginMap.get("timestamp"));
        httppost.addHeader("substationId",loginMap.get("substationId"));
        httppost.addHeader("version",loginMap.get("3.0"));
        httppost.setEntity(reqEntity);
        getResponseJsonObejct(httpclient,httppost);

    }

    /**
     * 商品详情中的优惠券
     * uid:用户id
     * goodsId:商品id
     * category
     */
    @Test
    public void selectByGoodsId() throws Exception{
        Map<String,Object> loginMap = login();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httppost = new HttpPost(ipAddress+"/android/shopcoupon/selectByGoodsId");
        final StringBody goodsId = new StringBody("105", ContentType.TEXT_PLAIN);
        final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("goodsId", goodsId)
                .build();
        httppost.addHeader("userToken",loginMap.get("userToken"));
        httppost.addHeader("userId",loginMap.get("userId"));
        httppost.addHeader("timestamp",loginMap.get("timestamp"));
        httppost.addHeader("substationId",loginMap.get("substationId"));
        httppost.setEntity(reqEntity);
        getResponseJsonObejct(httpclient,httppost);
    }

    /**
     * 提交订单
     * Exception
     */
    @Test
    public void addOrder() throws Exception{
        Map<String,Object> loginMap = login();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httppost = new HttpPost(ipAddress+"/android/shoporder/addorder200");
        final StringBody uid = new StringBody("5881", ContentType.TEXT_PLAIN);
        Calendar calendar = Calendar.getInstance();
        String timestamp = String.valueOf( calendar.getTimeInMillis() );
        final StringBody timestampVal = new StringBody(timestamp, ContentType.TEXT_PLAIN);
        final StringBody receiverAddressId = new StringBody("190", ContentType.TEXT_PLAIN);
        final StringBody skus = new StringBody("445#125#178#1", ContentType.TEXT_PLAIN);
        final StringBody cashPoints = new StringBody("0", ContentType.TEXT_PLAIN);
        final StringBody expressTypeId = new StringBody("1", ContentType.TEXT_PLAIN);
        final StringBody couponSchemeId = new StringBody("27", ContentType.TEXT_PLAIN);
        final StringBody couponId = new StringBody("7589", ContentType.TEXT_PLAIN);
        final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("uid", uid)
                .addPart("timestamp", timestampVal)
                .addPart("receiverAddressId", receiverAddressId)
                .addPart("skus", skus)
                .addPart("cashPoints", cashPoints)
                .addPart("schemeId", couponSchemeId)
                .addPart("couponId", couponId)
                .addPart("expressTypeId", expressTypeId)
                .build();
        httppost.addHeader("userToken",loginMap.get("userToken"));
        httppost.addHeader("userId",loginMap.get("userId"));
        httppost.addHeader("timestamp",loginMap.get("timestamp"));
        httppost.addHeader("substationId",loginMap.get("substationId"));
        httppost.setEntity(reqEntity);
        getResponseJsonObejct(httpclient,httppost);
    }

    /**
     *我的优惠券
     */
    @Test
    public void selectMyCouponList() throws Exception{
        Map<String,Object> loginMap = login();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httppost = new HttpPost(ipAddress+"/android/shopcoupon/selectmycouponlist");
        final StringBody userId = new StringBody("5881", ContentType.TEXT_PLAIN);
        final StringBody which = new StringBody("1", ContentType.TEXT_PLAIN);
        final StringBody lastId = new StringBody("37", ContentType.TEXT_PLAIN);
        final StringBody size = new StringBody("6", ContentType.TEXT_PLAIN);
        final StringBody userType = new StringBody("0", ContentType.TEXT_PLAIN);
        final StringBody goodsProper = new StringBody("114#167#15", ContentType.TEXT_PLAIN);
        final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("userId", userId)
                .addPart("which", which)
                .addPart("lastId", lastId)
                .addPart("size", size)
                .addPart("userType", userType)
                .addPart("goodsProper", goodsProper)
                .build();
        httppost.addHeader("userToken",loginMap.get("userToken"));
        httppost.addHeader("userId",loginMap.get("userId"));
        httppost.addHeader("timestamp",loginMap.get("timestamp"));
        httppost.addHeader("substationId",loginMap.get("substationId"));
        httppost.addHeader("machineCode","123456");
        httppost.setEntity(reqEntity);
        getResponseJsonObejct(httpclient,httppost);
    }

    /**
     * 订单详情
     * @throws Exception
     */
    @Test
    public void getOrderDetail() throws Exception{
        Map<String,Object> loginMap = login();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httppost = new HttpPost(ipAddress+"/android/shoporder/getorderdetail");
        final StringBody userId = new StringBody("5881", ContentType.TEXT_PLAIN);
        final StringBody orderNum = new StringBody("04450000588120180118134550449937", ContentType.TEXT_PLAIN);
        final StringBody curSubstationId = new StringBody("445", ContentType.TEXT_PLAIN);
        final StringBody size = new StringBody("6", ContentType.TEXT_PLAIN);
        final StringBody userType = new StringBody("0", ContentType.TEXT_PLAIN);
        final StringBody goodsProper = new StringBody("102#154#1,103#155#3", ContentType.TEXT_PLAIN);
        final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("uid", userId)
                .addPart("orderNum", orderNum)
                .addPart("curSubstationId", curSubstationId)
                .addPart("size", size)
                .addPart("userType", userType)
                .addPart("goodsProper", goodsProper)
                .build();
        httppost.addHeader("userToken",loginMap.get("userToken"));
        httppost.addHeader("userId",loginMap.get("userId"));
        httppost.addHeader("timestamp",loginMap.get("timestamp"));
        httppost.addHeader("substationId",loginMap.get("substationId"));
        httppost.addHeader("machineCode","123456");
        httppost.setEntity(reqEntity);
        getResponseJsonObejct(httpclient,httppost);
    }

    /**
     * 根据优惠券计算价格
     */
    @Test
    public void calculateCoupon() throws Exception{
        Map<String,Object> loginMap = login();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httppost = new HttpPost(ipAddress+"/android/goodscart/cartConfirmCoupon");
        final StringBody cartIds = new StringBody("1468", ContentType.TEXT_PLAIN);
        final StringBody couponSchemeId = new StringBody("2", ContentType.TEXT_PLAIN);
        final StringBody addrId = new StringBody("190", ContentType.TEXT_PLAIN);
        final HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("cartIds", cartIds)
                .addPart("couponSchemeId", couponSchemeId)
                .addPart("addrId", addrId)
                .build();
        httppost.addHeader("userToken",loginMap.get("userToken"));
        httppost.addHeader("userId",loginMap.get("userId"));
        httppost.addHeader("timestamp",loginMap.get("timestamp"));
        httppost.addHeader("substationId",loginMap.get("substationId"));
        httppost.addHeader("machineCode","123456");
        httppost.setEntity(reqEntity);
        getResponseJsonObejct(httpclient,httppost);
    }
}
