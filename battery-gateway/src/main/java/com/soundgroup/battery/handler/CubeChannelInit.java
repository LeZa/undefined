package com.soundgroup.battery.handler;

import com.soundgroup.battery.core.common.RocksDBHolder;
import com.soundgroup.battery.utils.newStringUtils.MsgDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import org.springframework.stereotype.Component;

@Component("CubeChannelInit")
public class CubeChannelInit extends ChannelInitializer<SocketChannel> {

    /**
     * MAX_FRAME_LENGTH:消息体的最大长度
     */
    private static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;

    private static final int LENGTH_FIELD_LENGTH = 4;

    private static final int LENGTH_FIELD_OFFSET = 1;

    private static final int LENGTH_ADJUSTMENT = 1;

    private static final int INITIAL_BYTES_TO_STRIP = 0;



    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast(new IdleStateHandler(40, 0, 0));
   /*     *//**
         *LengthFieldBasedFrameDecoder解码器(用于处理拆包和粘包问题)
         *参数释义
         * MAX_FRAME_LENGTH:消息体的最大长度
         * LENGTH_FIELD_OFFSET:偏移多少位之后才是我们的消息体
         * LENGTH_FIELD_LENGTH:Message类中的length的长度
         **/
/*        pipeline.addLast("frameDecoder",
                new LengthFieldBasedFrameDecoder(
                             MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
                                LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));*/
        pipeline.addLast("frameDecoder", new MsgDecoder());
/*        pipeline.addLast("frameEncoder",
                new MsgEncoder());*/
/*        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));*/
        pipeline.addLast("cubehandler", new CubeInboundHandler());
    }

}
