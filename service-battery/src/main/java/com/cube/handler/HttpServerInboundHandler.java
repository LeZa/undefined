package com.cube.handler;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.cube.core.CubeRun;
import com.cube.logic.HttpProcess;
import com.cube.logic.http.OpenExecAction;
import org.apache.log4j.Logger;

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

import com.cube.logic.HttpProcessRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class HttpServerInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static final Logger LOG = Logger.getLogger(HttpServerInboundHandler.class);
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
        
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.getUri());
        LOG.info("getUri=:"+req.getUri());
        String path = queryStringDecoder.path();
        

        // Send the demo page and favicon.ico
        if ("/".equals(path)) {
            ByteBuf content = getContent("返回build by purely");
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
//        dispatcher(ctx, req);
        if( "/open".equals(path) ){
            HttpProcess openExecAction = (OpenExecAction) applicationContext.getBean("openExecAction");
            openExecAction.execute(ctx,req);
        }else if("/close".equals(path)){
            HttpProcess openExecAction = (OpenExecAction) applicationContext.getBean("closeExecAction");
            openExecAction.execute(ctx,req);
        }else if("/battery".equals(path)){
            HttpProcess openExecAction = (OpenExecAction) applicationContext.getBean("batteryExecAction");
            openExecAction.execute(ctx,req);
        }

        // 处理业务
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }



    @Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		LOG.error("http server handler exception:", cause);
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
        	 LOG.info("remoteAddress============;"+ctx.channel().remoteAddress());
            ChannelFuture f = ctx.channel().writeAndFlush(res);
            if (!isKeepAlive(req) || res.getStatus().code() != 200) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
    
    private static void dispatcher(ChannelHandlerContext ctx, FullHttpRequest req){
        //在HttpProcessRunnable中已经req.retain()
        HttpProcessRunnable run = new HttpProcessRunnable(ctx, req);
        HttpProcessRunnable.EXECUTOR.submit(run);
    }

    private static ByteBuf getContent(String cnt) {
        return Unpooled.copiedBuffer("<html><head><title>Web Test</title></head>" + NEWLINE + "<body>" + NEWLINE
                + cnt + NEWLINE + "</body>" + NEWLINE + "</html>" + NEWLINE, CharsetUtil.UTF_8);
    }
}
