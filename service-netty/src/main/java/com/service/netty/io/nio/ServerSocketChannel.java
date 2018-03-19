package com.service.netty.io.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerSocketChannel {

    private  int port;

    public ServerSocketChannel( int port ){
        this.port = port;
    }

    /**
     *  /**
     * 对于ChannelOption.SO_BACKLOG的解释：
     * 服务器端TCP内核维护有两个队列，我们称之为A、B队列。客户端向服务器端connect时，会发送带有SYN标志的包（第一次握手），服务器端
     * 接收到客户端发送的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，然后服务器接收到
     * 客户端发送的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。也就是说accept
     * 从B队列中取出完成了三次握手的连接。
     * A队列和B队列的长度之和就是backlog。当A、B队列的长度之和大于ChannelOption.SO_BACKLOG时，新的连接将会被TCP内核拒绝。
     * 所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。要注意的是，backlog对程序支持的
     * 连接数并无影响，backlog影响的只是还没有被accept取出的连接
     */


    public void run(){
        /**用于处理服务器端接收客户端连接**/
        EventLoopGroup
                eventLoopGroup =  new NioEventLoopGroup();
        /**进行网络通信（读写）**/
        EventLoopGroup
                   eventLoopGroup1 = new NioEventLoopGroup();
        try{
            /**辅助工具类，用于服务器通道的一系列配置 **/
            ServerBootstrap
                        serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(eventLoopGroup,eventLoopGroup1)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                        protected  void initChannel( SocketChannel socketChannel){
                            socketChannel.pipeline().addLast( new ServerHandler());
                        }
                    })
                    /**设置TCP缓冲区**/
                    .option( ChannelOption.SO_BACKLOG,128)
                    /**设置发送数据缓冲大小 **/
                    .option( ChannelOption.SO_SNDBUF,32*1024)
                    /**设置接受数据缓冲大小 **/
                    .option( ChannelOption.SO_RCVBUF,32*1024)
                    /**保持连接**/
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture channelFuture
                                = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();

        }catch ( Exception exp ){
            exp.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
            eventLoopGroup1.shutdownGracefully();
        }

    }


    class ServerHandler
        extends ChannelInboundHandlerAdapter{

        @Override
        public void channelRead( ChannelHandlerContext channelHandlerContext,Object object) throws Exception {
             ByteBuf byteBuf = (ByteBuf)object;
             byte[] bytes = new byte[byteBuf.readableBytes()];
             byteBuf.readBytes( bytes );
             String request = new String( bytes,"UTF-8" );
             System.out.println( "Server: "+ request);
             String response = "Response msg";
             channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("88".getBytes()));
        }
    }

    public static void main(String sck[]){
        new ServerSocketChannel(6010).run();
    }
}
