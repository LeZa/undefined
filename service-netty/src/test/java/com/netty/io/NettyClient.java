package com.netty.io;

import com.service.netty.io.nio.NettyNioServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;


public class NettyClient {

    public static String HOST = "192.168.89.223";
    public static int PORT = 9151;

    public static Bootstrap bootstrap = getBootstrap();
    /**
     * 初始化Bootstrap
     * @return
     */
    public static final Bootstrap getBootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipeline.addLast("handler", new TcpClientHandler());
            }
        });
// b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    public static final Channel getChannel(String host,int port){
        Channel channel = null;
        try {
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (Exception e) {
            System.out.println("连接Server(IP[%s],PORT[%s])失败");
            e.printStackTrace();
            return null;
        }
        return channel;
    }

    public static void sendMsg(Channel channel, ByteBuf buf) throws Exception {
        if(channel!=null){
            channel.writeAndFlush(buf).sync();
        }else{
            System.out.println("消息发送失败,连接尚未建立!");
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            long t0 = System.nanoTime();
            byte[] value = null;
            Channel channel = null;
            for (int i = 0; i < 2; i++) {
                channel = getChannel(HOST, PORT);
                value = (i+",你好\n").getBytes();
                ByteBufAllocator alloc = channel.alloc();
                ByteBuf buf = alloc.buffer(value.length);
                buf.writeBytes(value);
                NettyClient.sendMsg(channel,buf);
            }
            long t1 = System.nanoTime();
            System.out.println((t1-t0)/1000000.0);
            Thread.sleep(5000);
            System.exit(0);
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
