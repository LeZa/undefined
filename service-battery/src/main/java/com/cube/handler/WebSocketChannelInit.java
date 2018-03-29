package com.cube.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import org.springframework.stereotype.Component;

@Component("WebSocketChannelInit")
public class WebSocketChannelInit extends ChannelInitializer<SocketChannel> {
	
	 private static final String WEBSOCKET_PATH = "/websocket";
	 

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
    	ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
    }

}
