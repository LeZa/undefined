package com.service.netty.io.nettyInAction.Echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoServerHandler
    extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext,
                                Object object){
        ByteBuf inByteBuf =  (ByteBuf) object;
        System.out.println("Server received.........."
                +inByteBuf.toString(CharsetUtil.UTF_8));
        ByteBuf outByteBuf =  Unpooled.wrappedBuffer( "Connect success\r\n".getBytes());
        channelHandlerContext.writeAndFlush(outByteBuf);
    }

    @Override
    public void channelReadComplete( ChannelHandlerContext channelHandlerContext){
            channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER)
                                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext channelHandlerContext,
                                    Throwable throwable){
            throwable.printStackTrace();
            channelHandlerContext.close();
    }

}
