package com.soundgroup.battery.logic.http;


import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.core.common.RocksDBHolder;
import com.soundgroup.battery.logic.DefaultHttpProcess;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

@Component("dayBatteryExecAction")
public class DayBatteryExecAction
        extends DefaultHttpProcess {
    protected void doExcute(ChannelHandlerContext ctx, FullHttpRequest req,
                            FullHttpResponse resp, Map<String, Object> paramMap, HttpPostRequestDecoder decoder) throws Exception {
        AnnotationConfigApplicationContext
                applicationContext = CubeRun.getApplicationContext();
        RocksDBHolder rocksDBHolder = (RocksDBHolder) applicationContext.getBean("rocksDBHolder");
        String sn = (String) paramMap.get("SN");
        String day =  (String) paramMap.get("day");
        String key = sn+day;
        byte[] result = rocksDBHolder.getResource().get( key.getBytes() );
        String content =  "";
        if( result != null
                && result.length > 0 ){
            content = new String( result,"UTF-8" );
        }
        resp.content().writeBytes( content.getBytes() );
        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
    }
}
