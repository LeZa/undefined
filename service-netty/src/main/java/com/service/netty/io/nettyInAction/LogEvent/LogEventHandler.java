package com.service.netty.io.nettyInAction.LogEvent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogEventHandler
    extends SimpleChannelInboundHandler<LogEvent>{

    @Override
    public void exceptionCaught(
            ChannelHandlerContext channelHandlerContext,Throwable throwable) throws Exception{
        throwable.printStackTrace();
        channelHandlerContext.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext,
                                        LogEvent logEvent) throws Exception {
                StringBuilder stringBuilder
                                = new StringBuilder(1024);
                stringBuilder.append( logEvent.getReceived() );
                stringBuilder.append(" [");
                stringBuilder.append(logEvent.getInetSocketAddress().toString());
                stringBuilder.append(" ] [");
                stringBuilder.append( logEvent.getLogfile() );
                stringBuilder.append("] : ");
                stringBuilder.append( logEvent.getMsg() );
                System.out.println( stringBuilder );
    }
}
