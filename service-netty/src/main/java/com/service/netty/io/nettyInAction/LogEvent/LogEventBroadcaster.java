package com.service.netty.io.nettyInAction.LogEvent;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

public class LogEventBroadcaster {

    private final EventLoopGroup eventLoopGroup;

    private final Bootstrap bootstrap;

    private final File file;

    public LogEventBroadcaster(InetSocketAddress inetSocketAddress,File file ){
         eventLoopGroup = new NioEventLoopGroup();
         bootstrap = new Bootstrap();
         bootstrap.group(eventLoopGroup)
                  .channel(NioDatagramChannel.class)
                  .option(ChannelOption.SO_BROADCAST,true)
                  .handler( new LogEventEncoder( inetSocketAddress  ));
         this.file = file;
    }

    public void run() throws  Exception{
        Channel channel = bootstrap.bind(0).sync().channel();
        long pointer = 0L;
        for(;;){
            long len = file.length();
            if( len < pointer ){
                pointer = len;
            }else if( len > pointer ){
                RandomAccessFile
                        randomAccessFile = new RandomAccessFile( file,"r");
                randomAccessFile.seek( pointer );
                String line;
                while ( ( line = randomAccessFile.readLine()) != null ){
                    channel.writeAndFlush(
                            new LogEvent(null,-1,file.getAbsolutePath(),line));
                }
                pointer = randomAccessFile.getFilePointer();
                randomAccessFile.close();
            }
            try{
                Thread.sleep( 1000L);
            }catch ( InterruptedException exp ){
                Thread.interrupted();
                break;
            }

        }

    }

    public void stop(){
        eventLoopGroup.shutdownGracefully();
    }

    public static void main( String sck[] ) throws Exception{
        LogEventBroadcaster logEventBroadcaster = new LogEventBroadcaster(
                            new InetSocketAddress("255.255.255.255",9156),
                                    new File("/home/cent/log.log"));
        try{
           logEventBroadcaster.run();
        }finally {
            logEventBroadcaster.stop();
        }
    }
}
