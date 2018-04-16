package com.soundgroup.battery.logic.http;

import com.google.gson.Gson;

import com.soundgroup.battery.conf.BatteryEntity;
import com.soundgroup.battery.conf.ResponseEntity;
import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.core.common.RocksDBHolder;
import com.soundgroup.battery.logic.DefaultHttpProcess;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

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

    @Override
    protected void doExcute(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp,
                            Map<String, Object> paramMap, HttpPostRequestDecoder decoder) throws Exception {
        Gson gson = new Gson();
        long curTimeinMillis = Calendar.getInstance().getTimeInMillis();
        curTimeinMillis = (curTimeinMillis/1000)-25;
        AnnotationConfigApplicationContext
                applicationContext = CubeRun.getApplicationContext();
        RocksDBHolder rocksDBHolder = (RocksDBHolder) applicationContext.getBean("rocksDBHolder");
        String sn = (String) paramMap.get("SN");
        byte[] result = rocksDBHolder.getResource().get(sn.getBytes());
        String retData =  new String( result );
        String[] retDataArr =  retData.split(",");
        int len = retDataArr.length;
        String curTimeMStr = retDataArr[ len-1 ];
        long storageCurTime =  Long.parseLong( curTimeMStr );
        String resultStr = new Gson().toJson(ResponseEntity.failRtn("off-line") );
        if( storageCurTime > curTimeinMillis ){
            BatteryEntity batteryEntity =  new BatteryEntity();
            batteryEntity.setDeviceCode( sn );
            String point_x = retDataArr[12] + retDataArr[13] + retDataArr[14] + retDataArr[15];
            batteryEntity.setPointX( point_x );
            String point_y = retDataArr[17] + retDataArr[18] + retDataArr[19] + retDataArr[20];
            batteryEntity.setPointY( point_y );
            String battery = retDataArr[16];
            batteryEntity.setPower( battery );
            resultStr =  new Gson().toJson( ResponseEntity.sussRtn("on-line",batteryEntity));
        }
        resp.content().writeBytes(new String(resultStr.toString().getBytes(),"UTF-8").getBytes());
        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
    }
}
