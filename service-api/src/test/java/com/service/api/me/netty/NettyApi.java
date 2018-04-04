package com.service.api.me.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

public class NettyApi {


    @Test
    public void byteBufApi(){
        ByteBuf byteBuf
                = Unpooled.wrappedBuffer(("*HQ,8168000008,V1,043602,A,2234.9273," +
                "N,11354.3980,E,000.06,000,100715,FBFFBBFF,460,00,10342,4283,23.4#").getBytes());

        System.out.println( byteBuf.getUnsignedByte(1) );

        System.out.println( byteBuf.getUnsignedShort(2) );

        System.out.println( byteBuf.getUnsignedMedium(3) );

        System.out.println( byteBuf.getUnsignedInt(4) );

        System.out.println( byteBuf.getLong(8) );

    }
}
