package com.build.pattern.reactor;


import com.build.pattern.reactor.connection.*;

public interface EventLoop extends Runnable {
    void register(Connection connection);
}
