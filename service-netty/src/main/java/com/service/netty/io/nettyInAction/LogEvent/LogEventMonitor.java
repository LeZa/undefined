package com.service.netty.io.nettyInAction.LogEvent;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

public class LogEventMonitor {

    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;

    public LogEventMonitor(InetSocketAddress inetSocketAddress){

//        eventLoopGroup = new NioEventLoopGroup();
        eventLoopGroup = new EpollEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group( eventLoopGroup)
//                 .channel(NioDatagramChannel.class)
                 .channel(EpollDatagramChannel.class)
                 .option(ChannelOption.SO_BROADCAST,true)
                 .handler(new ChannelInitializer<Channel>() {

                     @Override
                     public void initChannel( Channel channel)
                            throws Exception{
                         ChannelPipeline channelPipeline
                                            = channel.pipeline();
                         channelPipeline.addLast( new LogEventDecoder() );
                         channelPipeline.addLast( new LogEventHandler() );
                     }
                 }).localAddress(inetSocketAddress);
    }

    public Channel bind(){
        return bootstrap.bind().syncUninterruptibly().channel();
    }

    public void stop(){
        eventLoopGroup.shutdownGracefully();
    }

    public static void main( String sck[] ) throws Exception{
            LogEventMonitor logEventMonitor
                                    = new LogEventMonitor( new InetSocketAddress( 9213) );
            try {
                Channel channel = logEventMonitor.bind();
                System.out.println("LogEventMonitor running");
                channel.closeFuture().sync();
            }finally {
                logEventMonitor.stop();
            }
    }
}
