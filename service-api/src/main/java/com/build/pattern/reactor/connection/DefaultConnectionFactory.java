package com.build.pattern.reactor.connection;

import com.build.pattern.reactor.*;

import java.nio.channels.SocketChannel;

public class DefaultConnectionFactory implements ConnectionFactory {

    @Override
    public Connection newConnection(EventLoop eventLoop, SocketChannel channel) {
        return new DefaultConnection(eventLoop, channel);
    }
}
