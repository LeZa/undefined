package com.soundgroup.battery.logic;

import com.soundgroup.battery.conf.CommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.soundgroup.battery.event.CubeMsg;

public class TransforEncoder extends MessageToByteEncoder<CubeMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CubeMsg msg, ByteBuf out) throws Exception {
    //编码器
    	byte[] datas = CommonUtil.ObjectToByte(msg);
        out.writeBytes(datas);  
        ctx.flush();  
    }

}
