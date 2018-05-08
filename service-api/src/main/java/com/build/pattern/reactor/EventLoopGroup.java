package com.build.pattern.reactor;


import com.build.pattern.reactor.connection.*;

public interface EventLoopGroup {

    EventLoop next();

    void register(Connection connection);

}
