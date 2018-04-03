package com.cube.logic.http;

import com.cube.conf.ResponseEntity;
import com.cube.event.CubeMsg;
import com.cube.event.EventEnum;
import com.cube.event.ReplyEvent;
import com.cube.logic.DefaultHttpProcess;
import com.cube.server.CubeBootstrap;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.util.ReferenceCountUtil;

import org.apache.commons.lang.StringUtils;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;


/**
 * @Description  Close battery
 * @author sushile
 * @date 20180328
 */
@Component("closeExecAction")
public class CloseExecAction
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
            cubeMsg.setType(EventEnum.CLOSE_BATTERY);
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
