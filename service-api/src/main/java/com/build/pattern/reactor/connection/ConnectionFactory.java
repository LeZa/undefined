package com.build.pattern.reactor.connection;


import com.build.pattern.reactor.*;

import java.nio.channels.SocketChannel;

public interface ConnectionFactory {

    Connection newConnection(EventLoop eventLoop, SocketChannel channel);
}
