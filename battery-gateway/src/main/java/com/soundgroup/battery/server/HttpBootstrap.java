package com.soundgroup.battery.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import javax.annotation.Resource;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class HttpBootstrap implements Runnable {

	private static final Logger LOG = Logger.getLogger(HttpBootstrap.class);
    private volatile boolean run = false;

    @Autowired
    private ServerBootstrap serverBootstrap;
    @Resource(name = "CubeHttpChannelInit")
    private ChannelInitializer<SocketChannel> cubeChannelInit;
    @Resource(name = "boss")
    private EventLoopGroup boss;
    @Resource(name = "cubeHttpWorker")
    private EventLoopGroup worker;

    @Value("${http.port}")
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
            LOG.info("等待HTTP服务结束...........................");
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.info("interrupted", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}

/**
 * @Description ChannelOption.TCP_NODELAY

为什么延迟不高不低正好 40ms 呢？果断 Google 一下找到了答案。原来这是 TCP 协议中的 Nagle‘s Algorithm 和 TCP Delayed Acknoledgement 共同起作 用所造成的结果。

Nagle’s Algorithm 是为了提高带宽利用率设计的算法，其做法是合并小的TCP 包为一个，避免了过多的小报文的 TCP 头所浪费的带宽。如果开启了这个算法 （默认），则协议栈会累积数据直到以下两个条件之一满足的时候才真正发送出 去：

积累的数据量到达最大的 TCP Segment Size
收到了一个 Ack

TCP Delayed Acknoledgement 也是为了类似的目的被设计出来的，它的作用就 是延迟 Ack 包的发送，使得协议栈有机会合并多个 Ack，提高网络性能。
如果一个 TCP 连接的一端启用了 Nagle‘s Algorithm，而另一端启用了 TCP Delayed Ack，而发送的数据包又比较小，则可能会出现这样的情况：发送端在等 待接收端对上一个packet 的 Ack 才发送当前的 packet，而接收端则正好延迟了 此 Ack 的发送，那么这个正要被发送的 packet 就会同样被延迟。当然 Delayed Ack 是有个超时机制的，而默认的超时正好就是 40ms。
现代的 TCP/IP 协议栈实现，默认几乎都启用了这两个功能，你可能会想，按我 上面的说法，当协议报文很小的时候，岂不每次都会触发这个延迟问题？事实不 是那样的。仅当协议的交互是发送端连续发送两个 packet，然后立刻 read 的 时候才会出现问题。
 */
