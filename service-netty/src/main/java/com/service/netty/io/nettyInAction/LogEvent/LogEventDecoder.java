package com.service.netty.io.nettyInAction.LogEvent;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class LogEventDecoder
    extends MessageToMessageDecoder<DatagramPacket> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          DatagramPacket datagramPacket, List<Object> list) throws Exception {

        ByteBuf
                byteBuf = datagramPacket.content() ;
        String logMs1g = byteBuf.toString( CharsetUtil.UTF_8);
        String[] msgArr = logMs1g.split(":");
        LogEvent
                logEvent = new LogEvent( datagramPacket.sender(),System.currentTimeMillis(),msgArr[0],msgArr[1] );
        list.add(logEvent );
    }
}
