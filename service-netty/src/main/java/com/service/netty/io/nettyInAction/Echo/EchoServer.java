package com.service.netty.io.nettyInAction.Echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer( int port ){
        this.port = port;
    }

    public static void main( String sck[] ) throws Exception{
        new EchoServer(9212).start();
    }

    public void start()throws Exception {
        final EchoServerHandler
                    echoServerHandler = new EchoServerHandler();
        EventLoopGroup eventLoopGroup =  new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(echoServerHandler);
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

}
