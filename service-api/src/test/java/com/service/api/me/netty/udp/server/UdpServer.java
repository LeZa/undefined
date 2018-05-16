package com.service.api.me.netty.udp.server;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer {


    public void run( int port ) throws InterruptedException {

        EventLoopGroup group
                        = new NioEventLoopGroup();
        Bootstrap bootstrap
                        = new Bootstrap();
        bootstrap.group( group)
                 .channel(NioDatagramChannel.class)
                 .option( ChannelOption.SO_BROADCAST,true)
                 .handler( new UdpServerHandler());
        bootstrap.bind( port )
                 .sync()
                 .channel()
                 .closeFuture()
                 .await();

    }
}
