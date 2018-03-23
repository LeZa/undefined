package com.service.netty.io.nettyInAction.LogEvent;

import java.net.InetSocketAddress;

public class LogEvent {

    public static final byte SEPARATOR = (byte)':';

    private  final InetSocketAddress  inetSocketAddress;

    private final String logfile;

    private final String msg;

    private final long received;

    public LogEvent( String logfile,String msg ){
        this(null,-1,logfile,msg);
    }

    public LogEvent( InetSocketAddress inetSocketAddress,long received,String logfile,String msg ){
        this.inetSocketAddress = inetSocketAddress;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }


    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceived() {
        return received;
    }
}
