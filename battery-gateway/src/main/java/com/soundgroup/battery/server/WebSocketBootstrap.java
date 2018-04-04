package com.soundgroup.battery.server;

import com.soundgroup.battery.handler.WebSocketChannelInit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class WebSocketBootstrap implements Runnable {

	private static final Logger LOG = Logger.getLogger(WebSocketBootstrap.class);
    private volatile boolean run = false;

    @Autowired
    private ServerBootstrap serverBootstrap;
    @Resource(name = "WebSocketChannelInit")
    private WebSocketChannelInit cubeChannelInit;
    @Resource(name = "boss")
    private EventLoopGroup boss;
    @Resource(name = "cubeWebSocketWorker")
    private EventLoopGroup worker;

    @Value("${web.port}")
    private String httpPort;

    public void run() {

        LOG.info("设置HttpBootstrap");
        // 设置工作线程池
        serverBootstrap.group(boss, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.childHandler(cubeChannelInit);
        serverBootstrap.childOption(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 32 * 1024);
        serverBootstrap.childOption(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 8 * 1024);
        // 设置为pooled的allocator,
        // netty4.0这个版本默认是unpooled,必须设置参数-Dio.netty.allocator.type pooled,
        // 或直接指定pooled
        serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 直接发包
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        Channel ch;
        try {

            ChannelFuture bindf = serverBootstrap.bind(Integer.valueOf(httpPort));
            ChannelFuture bsync = bindf.sync();
            ch = bsync.channel();
            LOG.info((new StringBuilder()).append("listen port=:").append(httpPort).toString());
            LOG.info("等待HTTP服务结束...");
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.info("interrupted", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    
}
