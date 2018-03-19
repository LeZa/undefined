package com.service.netty.io.oio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyOioServer {

    public void server( int port ) throws InterruptedException {
        final ByteBuf byteBuffer
                    = Unpooled.unreleasableBuffer(
                               Unpooled.copiedBuffer("Hi! 6010 \r\n", Charset.forName("UTF-8")));
        EventLoopGroup
                    eventLoopGroup =  new OioEventLoopGroup();
        try {
            ServerBootstrap
                    serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(OioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext channelHandlerContext) throws InterruptedException {
                                            Thread.sleep(400L);
                                            channelHandlerContext.writeAndFlush(byteBuffer.duplicate())
                                                    .addListener(ChannelFutureListener.CLOSE);
                                        }
                                    });
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main( String sck[] ){
        NettyOioServer nettyOioServer = new NettyOioServer();
        try {
            nettyOioServer.server(6010);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
