package com.soundgroup.battery.logic;

import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import com.soundgroup.battery.conf.ResponseEntity;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.soundgroup.battery.exception.RespException;

public abstract class DefaultHttpProcess
        implements HttpProcess {

    public void execute(ChannelHandlerContext ctx, FullHttpRequest req,Map<String,Object> paramMap)  {
        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        Map<String, List<String>> params = null;
        HttpPostRequestDecoder decoder = null;
        try {
            if (req.getMethod() == HttpMethod.GET) {
                QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.getUri());
                params = queryStringDecoder.parameters();
            } else if (req.getMethod() == HttpMethod.POST) {
                decoder = new HttpPostRequestDecoder(req);
            } else {
                throw new Exception("Not support request method.");
            }

            doExcute(ctx, req, resp, paramMap, decoder);

            sendResp(ctx, resp);

        } catch (RespException e) {
            e.printStackTrace();
        } catch (Exception e) {
            if(ctx.channel().isWritable()){
                resp.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                sendResp(ctx, resp);
            }else{
                if(resp.refCnt() >= 1){
                    resp.release();
                }
                ctx.channel().close();
            }
        } 
    }

    protected abstract void doExcute(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp,
            Map<String, Object> params, HttpPostRequestDecoder decoder) throws Exception;

    private String parseData(InterfaceHttpData data) throws IOException {
        if (data == null) {
            return null;
        }
        if (data.getHttpDataType() == HttpDataType.Attribute) {
            Attribute attribute = (Attribute) data;
            String v = attribute.getValue();
            return v;
        }
        return null;
    }

    private InterfaceHttpData getHttpData(String name, HttpPostRequestDecoder decoder) {
        if (decoder == null) {
            return null;
        }
        return decoder.getBodyHttpData(name);
    }

    private List<InterfaceHttpData> getHttpDatas(String name, HttpPostRequestDecoder decoder) {
        if (decoder == null) {
            return Collections.emptyList();
        }
        List<InterfaceHttpData> retLst = decoder.getBodyHttpDatas(name);
        if (retLst == null) {
            return Collections.emptyList();
        }
        return retLst;
    }

    private String getParam(String name, Map<String, List<String>> params) {
        if (params == null) {
            return null;
        }
        List<String> retLst = params.get(name);
        if (retLst == null) {
            return null;
        }
        if (!retLst.isEmpty()) {
            return retLst.get(0);
        } else {
            return null;
        }
    }

    private List<String> getParams(String name, Map<String, List<String>> params) {
        if (params == null) {
            return null;
        }
        List<String> retLst = params.get(name);
        if (retLst == null) {
            return Collections.emptyList();
        }
        return retLst;
    }

    private void sendResp(ChannelHandlerContext ctx, FullHttpResponse resp) {
        if (resp.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(resp.getStatus().toString(), CharsetUtil.UTF_8);
            resp.content().writeBytes(buf);
            buf.release();
        }

        if (ctx.channel().isWritable()) {
            setContentLength(resp, resp.content().readableBytes());
            ctx.channel().writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.channel().close();
        }
    }

    private HttpMethod method(FullHttpRequest req) {
        return req.getMethod();
    }

    protected void sendTimeout(FullHttpResponse resp) {
        Gson gson =  new Gson();
        resp.content().writeBytes(gson.toJson(ResponseEntity.failRtn("Request timeout.")).getBytes());
        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
    }

    protected void sendUnableConn(FullHttpResponse resp) {
        Gson gson = new Gson();
        resp.content().writeBytes( gson.toJson(
                ResponseEntity.failRtn("Device connect fial.")).getBytes() );
        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
    }

    private void sendParamsError(String msg, FullHttpResponse resp) {
        resp.content().writeBytes(msg.getBytes());
        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
    }
}
