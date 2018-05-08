package com.build.pattern.reactor.handler;


import com.build.pattern.reactor.connection.*;

public interface ConnectionInHandler {

    void connectionRegistered(ConnectionPipeline pipeline) throws Exception;

    void connectionUnregistered(ConnectionPipeline pipeline) throws Exception;

    void connectionRead(ConnectionPipeline pipeline, Object msg) throws Exception;

    void exceptionCaught(ConnectionPipeline pipeline, Throwable cause) throws Exception;
}
