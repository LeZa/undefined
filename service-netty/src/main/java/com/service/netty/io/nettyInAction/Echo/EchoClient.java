package com.service.netty.io.nettyInAction.Echo;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient( String host,int port){
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        EventLoopGroup eventLoopGroup
                        = new NioEventLoopGroup(4);
        try{
            Bootstrap bootstrap
                         = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                     .channel(NioSocketChannel.class)
                     .remoteAddress( new InetSocketAddress(host,port))
                     .handler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         public void initChannel( SocketChannel socketChannel){
                                socketChannel.pipeline().addLast( new EchoClientHandler());
                         }
                     });
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public  static void main( String sck[] ) throws Exception{
        new EchoClient("192.168.89.223",9212).start();
    }
}
