package com.service.netty.io.nettyInAction.LogEvent;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

public class LogEventEncoder
    extends MessageToMessageEncoder<LogEvent>{

    private final InetSocketAddress inetSocketAddress;

    public LogEventEncoder( InetSocketAddress inetSocketAddress ){
        this.inetSocketAddress = inetSocketAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          LogEvent logEvent, List<Object> list) throws Exception {
        byte[] file = logEvent.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf byteBuf =
                    channelHandlerContext.alloc()
                    .buffer( file.length + msg.length + 1);
        byteBuf.writeBytes(file);
        byteBuf.writeByte( LogEvent.SEPARATOR);
        byteBuf.writeBytes(msg);
        list.add(new DatagramPacket(byteBuf,inetSocketAddress));
    }
}
