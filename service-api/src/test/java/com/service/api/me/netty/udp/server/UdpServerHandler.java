package com.service.api.me.netty.udp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.DatagramPacket;
import java.util.concurrent.ThreadLocalRandom;

public class UdpServerHandler
        extends SimpleChannelInboundHandler<DatagramPacket>{

    //谚语列表
    private static final String[] DICTIONARY = { "只要功夫深，铁棒磨成针。",
            "旧时王谢堂前燕,飞入寻常百姓家。", "洛阳亲友如相问，一片冰心在玉壶。", "一寸光阴一寸金，寸金难买寸光阴。",
            "老骥伏枥，志在千里，烈士暮年，壮心不已" };

    private String nextQuote(){
        //返回0-DICTIONARY.length中的一个整数。
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];//将谚语列表中对应的谚语返回
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {

    }
}
