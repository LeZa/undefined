package com.soundgroup.battery.logic.http;

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

        ByteBuf buf = null;
        ReplyEvent replyEvent = null;
        try {
            String CODE = (String) paramMap.get("CODE");;
            String sn = (String) paramMap.get("SN");
            CubeMsg cubeMsg = CubeMsg.buildMsg(sn);
            if (cubeMsg == null) {
                sendUnableConn(resp);
                return;
            }
            replyEvent=new ReplyEvent(sn);
            CubeBootstrap.processRunnable.putReply(replyEvent);
            cubeMsg.setType(EventEnum.HTTP_BATTERY_INFO);
            cubeMsg.setData(sn.getBytes());//SN
            cubeMsg.setDataString(CODE);
            CubeBootstrap.processRunnable.pushUpMsg(cubeMsg);
            synchronized (replyEvent) {
                try {
                    if (replyEvent.getObj() == null) {
                        replyEvent.wait(2000);
                    }
                    replyEvent = CubeBootstrap.processRunnable.getReply(replyEvent.getId());
                    if (replyEvent == null || replyEvent.getObj() == null) {
                        sendTimeout(resp);
                    } else {
                        String retData = (String) replyEvent.getObj();
                        resp.content().writeBytes(new String(retData.toString().getBytes(),"UTF-8").getBytes());
                        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
                    }
                } catch (InterruptedException e) {
                    sendTimeout(resp);
                }
            }
        } finally {
            if (buf != null) {
                ReferenceCountUtil.release(buf);
            }
            if (replyEvent != null) {
                CubeBootstrap.processRunnable.removeReply(replyEvent.getId());
            }
        }
    }
}
