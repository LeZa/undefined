package com.soundgroup.battery.utils.newStringUtils;

import java.util.Calendar;
import java.util.List;

import com.soundgroup.battery.conf.CommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        int len = buf.readableBytes();
        if (len > 0) {
            byte[] bytes = new byte[len];
            buf.readBytes(bytes);
            String heartMsg = new String(bytes);
            if (heartMsg.startsWith("*")
                    && heartMsg.endsWith("#")) {
                if (heartMsg.split(",").length == 18) {
                    ByteBuf heartByteBuf = Unpooled.wrappedBuffer(heartMsg.getBytes());
                    list.add( heartMsg);
                }
            } else {
                String waterMsg = CommonUtil.bytesToHexString(bytes);
                /*String waterMsg = "24,21,70,05,52,41,01,29,32,12,04,18,22,36,3611,20,07,11,34,99,10,3E,00,12,33,FF,FF,FF,FF,01,0F,00,00,00,00,00,00,01,CC,00,25,EF,11,4F,5E";*/
                if (waterMsg.startsWith("24")
                        && waterMsg.length() == 134) {
                    long timeInMillis = Calendar.getInstance().getTimeInMillis();
                    waterMsg = waterMsg+","+timeInMillis;
                    ByteBuf waterByteBuf = Unpooled.copiedBuffer(waterMsg.getBytes());
                    list.add(waterByteBuf);
                }
            }
        }
    }


}
