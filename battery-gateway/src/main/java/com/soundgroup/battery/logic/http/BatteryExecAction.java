package com.soundgroup.battery.logic.http;

import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.core.common.RocksDBHolder;
import com.soundgroup.battery.event.CubeMsg;
import com.soundgroup.battery.event.EventEnum;
import com.soundgroup.battery.event.ReplyEvent;
import com.soundgroup.battery.logic.DefaultHttpProcess;
import com.soundgroup.battery.server.CubeBootstrap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.util.ReferenceCountUtil;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

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
        AnnotationConfigApplicationContext
                applicationContext = CubeRun.getApplicationContext();
        RocksDBHolder    rocksDBHolder = (RocksDBHolder) applicationContext.getBean("rocksDBHolder");
        String sn = (String) paramMap.get("SN");
        byte[] result = rocksDBHolder.getResource().get(sn.getBytes());
        String retData =  new String( result);
        resp.content().writeBytes(new String(retData.toString().getBytes(),"UTF-8").getBytes());
        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
    }
}
