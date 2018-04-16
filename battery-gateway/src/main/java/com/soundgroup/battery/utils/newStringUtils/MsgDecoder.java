package com.soundgroup.battery.utils.newStringUtils;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Calendar;
import java.util.List;

import com.soundgroup.battery.conf.CommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.util.CharsetUtil;
import org.rocksdb.RocksDB;

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
                }
            } else {
                String waterMsg = CommonUtil.bytesToHexString(bytes);
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
