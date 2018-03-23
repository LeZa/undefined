package com.netty.io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class TcpClientHandler
        extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if(msg instanceof ByteBuf){
            ByteBuf buf = (ByteBuf)msg;
            byte[] dst = new byte[buf.capacity()];
            buf.readBytes(dst);
            ReferenceCountUtil.release(msg);
        }else {
            System.out.println("error object");
        }

    }
}
