package com.soundgroup.battery.utils.newStringUtils;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Calendar;
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
            byte[] bytes = new byte[len];
            buf.readBytes(bytes);
         String heartMsg = new String(bytes);

         if( heartMsg.startsWith("*")
                    && heartMsg.endsWith("#")){
             if( heartMsg.split(",").length == 18){
                 ByteBuf heartByteBuf = Unpooled.wrappedBuffer( heartMsg.getBytes());
             }
         }else{
             String waterMsg =  bytesToHexString(bytes);
             if( waterMsg.startsWith("24")
                        && waterMsg.length() == 134 ) {
                 ByteBuf waterByteBuf = Unpooled.copiedBuffer(waterMsg.getBytes());
                 list.add(waterByteBuf);
             }
         }
        }
	}


    private static String bytesToHexString(byte[] src) {
        StringBuffer sb = new StringBuffer("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
            if (i != src.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
