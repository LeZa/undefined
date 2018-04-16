package com.soundgroup.battery.handler;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.soundgroup.battery.conf.CommonUtil;
import com.soundgroup.battery.conf.ResponseEntity;
import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.logic.HttpProcess;
import com.soundgroup.battery.logic.http.BatteryExecAction;
import com.soundgroup.battery.logic.http.CloseExecAction;
import com.soundgroup.battery.logic.http.DayBatteryExecAction;
import com.soundgroup.battery.logic.http.OpenExecAction;
import com.google.gson.Gson;
import com.soundgroup.battery.logic.HttpProcessRunnable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;


public class HttpServerInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public static final String NEWLINE = "\r\n";

    @Autowired
    private OpenExecAction openExecAction;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        // Handle a bad request.
        FullHttpRequest req = (FullHttpRequest) msg;
        if (!req.getDecoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        /**
         * @Description  Check  http method.
         */
        String methodName = req.getMethod().name().toUpperCase();
        Gson gson =  new Gson();
        ByteBuf responseStr = null;
        if( !methodName.equals("POST") ){
            responseStr = getContent(gson.toJson(ResponseEntity.failRtn("请求格式有误")));
        }
        /**
         * @Description  Check param=
         */
        int len = req.content().readableBytes();
        Map<String ,Object> paramMap =  new HashMap<String,Object>();
        if( len > 0 ){
            byte[] content = new byte[len];
            req.content().readBytes(content);
            String contentStr = new String( content,"UTF-8");
            paramMap = CommonUtil.url2Map(contentStr);
            if( paramMap.containsKey("SN")
                    && paramMap.containsKey("CODE")){}else{
                responseStr = getContent(gson.toJson(ResponseEntity.failRtn("请求参数有误/缺少参数")));
            }
        }else{
            ByteBuf content = getContent(gson.toJson(ResponseEntity.failRtn("请求参数有误/缺少参数")));
        }

        if( responseStr != null ){
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK,responseStr );
            res.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
            setContentLength(res, responseStr.readableBytes());
            sendHttpResponse(ctx, req, res);
            return;
        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.getUri());
        String path = queryStringDecoder.path();

        // Send the demo page and favicon.ico
        if ("/".equals(path)) {
            ByteBuf content = getContent(gson.toJson(ResponseEntity.failRtn("请添加请求路径")));
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
            res.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
            setContentLength(res, content.readableBytes());
            sendHttpResponse(ctx, req, res);
            return;
        }
        if ("/favicon.ico".equals(path)) {
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            sendHttpResponse(ctx, req, res);
            return;
        }

        AnnotationConfigApplicationContext
                        applicationContext = CubeRun.getApplicationContext();
        HttpProcess httpProcess = null;
        //    dispatcher(ctx, req);
        if( "/open".equals(path) ){
            httpProcess = (OpenExecAction) applicationContext.getBean("openExecAction");
        }else if("/close".equals(path)){
            httpProcess = (CloseExecAction) applicationContext.getBean("closeExecAction");
        }else if("/battery".equals(path)){
            httpProcess = (BatteryExecAction) applicationContext.getBean("batteryExecAction");
        }else if("/dayBattery".equals(path)){
            httpProcess = (DayBatteryExecAction) applicationContext.getBean("dayBatteryExecAction");
        }

        httpProcess.execute(ctx,req,paramMap);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
	}

	public static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        // Send the response and close the connection if necessary.
        if(ctx.channel().isWritable()){
            ChannelFuture f = ctx.channel().writeAndFlush(res);
            if (!isKeepAlive(req) || res.getStatus().code() != 200) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
    
    private static void dispatcher(ChannelHandlerContext ctx, FullHttpRequest req){
        HttpProcessRunnable run = new HttpProcessRunnable(ctx, req);
        HttpProcessRunnable.EXECUTOR.submit(run);
    }

    private static ByteBuf getContent(String cnt) {
      return Unpooled.copiedBuffer(cnt.getBytes());
    }
}
