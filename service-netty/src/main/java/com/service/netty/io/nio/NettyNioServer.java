package com.service.netty.io.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyNioServer {

    public void server( int port ) throws InterruptedException {
        final ByteBuf byteBuffer
                = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi! 6030 \r\n", Charset.forName("UTF-8")));

        EventLoopGroup eventLoopGroup
                        = new NioEventLoopGroup();
        ServerBootstrap
                    serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new ChannelInboundHandlerAdapter() {

                                        @Override
                                        public void channelRead( ChannelHandlerContext context,Object object){
                                            context.channel()
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext channelHandlerContext) {
                                            channelHandlerContext.writeAndFlush(byteBuffer.duplicate())
                                                    .addListener(ChannelFutureListener.CLOSE);
                                        }
                                    });
                        }
                    });
            ChannelFuture channelFuture
                    = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }


    class FrontendHandler extends SimpleChannelInboundHandler<Object>{

        private Session session;

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        }
    }

    public static void main( String sck[] ){
        NettyNioServer nettyNioServer
                         = new NettyNioServer();
        try {
            nettyNioServer.server(6030);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
