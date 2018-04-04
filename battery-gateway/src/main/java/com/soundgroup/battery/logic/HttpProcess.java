package com.soundgroup.battery.logic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;

public interface HttpProcess {

    public void execute(ChannelHandlerContext ctx, FullHttpRequest req,Map<String,Object> paramMap);
}
