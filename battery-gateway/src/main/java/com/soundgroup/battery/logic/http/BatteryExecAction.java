package com.soundgroup.battery.logic.http;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.soundgroup.battery.conf.BatteryEntity;
import com.soundgroup.battery.conf.CommonUtil;
import com.soundgroup.battery.conf.ResponseEntity;
import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.core.common.MongoDBOperator;
import com.soundgroup.battery.logic.DefaultHttpProcess;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import org.bson.Document;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

/**
 * @Description  battery info
 * @author sushile
 * @date 20180328
 */
@Component("batteryExecAction")
public class BatteryExecAction
        extends DefaultHttpProcess{

    private MongoDatabase mongoDatabase;

    /**
     * @Description  Compare getTimeInMillis and storage timeInMillis;
     * @param ctx
     * @param req
     * @param resp
     * @param paramMap
     * @param decoder
     * @throws Exception
     */
    @Override
    protected void doExcute(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp,
                            Map<String, Object> paramMap, HttpPostRequestDecoder decoder) throws Exception {

        Gson gson = new Gson();
        long curTimeinMillis = Calendar.getInstance().getTimeInMillis();//now time
        curTimeinMillis = (curTimeinMillis/1000)-25;

        AnnotationConfigApplicationContext applicationContext
                = CubeRun.getApplicationContext();
        mongoDatabase = (MongoDatabase) applicationContext.getBean("mongoDatabase");
        MongoCollection mongoCollection1 = MongoDBOperator.mongoCollection(mongoDatabase,"heart");
        String sn = (String) paramMap.get("SN");
        FindIterable<Document> findIterable1 = MongoDBOperator.hasNext(mongoCollection1,"heart",sn);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class, new GsonNullTypeAdapter());
        MongoCursor<Document> mongoCursor1 =  findIterable1.iterator();
        String resultStr = gsonBuilder.create().toJson(ResponseEntity.failRtn("device noit exists","-99",new BatteryEntity()));
        if(  mongoCursor1.hasNext() ) {
            Document document = findIterable1.iterator().next();
            String heartVal = (String) document.get(sn);

            String[] retDataArr = heartVal.split(",");
            int len = retDataArr.length;
            String curTimeMStr = retDataArr[len - 1];
            long storageCurTime = (Long.parseLong(curTimeMStr)) / 1000;//storage time
            BatteryEntity batteryEntity = null;
            if (storageCurTime > curTimeinMillis) {
                batteryEntity = getBatteryEntity(retDataArr, sn);
                resultStr = new Gson().toJson(ResponseEntity.sussRtn("on-line", batteryEntity));
            } else {
                batteryEntity = getBatteryEntity(retDataArr, sn);
                resultStr = new Gson().toJson(ResponseEntity.failRtn("off-line","-1", batteryEntity));
            }
        }
        resp.content().writeBytes(new String(resultStr.toString().getBytes(),"UTF-8").getBytes());
        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
    }

    /**
     * @Description getEntity
     * @param dataArr
     * @param sn
     * @return
     */
    private static BatteryEntity getBatteryEntity( String[] dataArr,String sn ){
        BatteryEntity batteryEntity =  new BatteryEntity();
        int len = dataArr.length;
        batteryEntity.setDeviceCode( sn );
        String point_x = dataArr[12] + dataArr[13] + dataArr[14] + dataArr[15];
        batteryEntity.setPointX( point_x );
        String point_y = dataArr[17] + dataArr[18] + dataArr[19] + dataArr[20];
        batteryEntity.setPointY( point_y );
        batteryEntity.setPower( dataArr[16] );
        batteryEntity.setLastReceTime( dataArr[len-1] );
        String status = dataArr[25]+dataArr[26]+dataArr[27]+dataArr[28];
        status = CommonUtil.hexToBinaryString(status);
        batteryEntity.setStatus(status);
        return  batteryEntity;
    }

    static class GsonNullTypeAdapter
            extends TypeAdapter{

        public void write(JsonWriter out, Object value) throws IOException {
            if (value == null) {
                out.value("");
                return;
            }
            out.value( String.valueOf(value) );
        }

        public Object read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return "";
            }
            return in.nextString();
        }
    }
}
