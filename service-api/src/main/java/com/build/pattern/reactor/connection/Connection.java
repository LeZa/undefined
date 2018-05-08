package com.build.pattern.reactor.connection;


import com.build.pattern.reactor.*;

import java.nio.channels.SocketChannel;

public interface Connection {

    EventLoop eventLoop();

    SocketChannel channel();

    ConnectionPipeline pipeline();

    void read();

    void write();
}
