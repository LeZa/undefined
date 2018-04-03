package com.cube.utils.newStringUtils;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.util.CharsetUtil;



public class MsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        int len = buf.readableBytes();
        if(  len > 0 ){
         String msg = buf.readBytes(len).toString(CharsetUtil.UTF_8).trim();
         if( msg.startsWith("*")
                    && msg.endsWith("#")){
             if( msg.split(",").length == 18){
                 ByteBuf msgBuf = Unpooled.wrappedBuffer( msg.getBytes());
                 list.add(msgBuf);
             }
         }
        }
	}
}
