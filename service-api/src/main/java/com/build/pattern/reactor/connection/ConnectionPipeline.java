package com.build.pattern.reactor.connection;


import com.build.pattern.reactor.handler.*;

public interface ConnectionPipeline {

    Connection connection();

    ConnectionPipeline add(String name, ConnectionHandler handler);

    ConnectionPipeline remove(String name);

    ConnectionPipeline fireRegistered();

    ConnectionPipeline fireUnregistered();

    ConnectionPipeline fireRead(Object msg);

    ConnectionPipeline fireWrite(Object msg);

    ConnectionPipeline flush();

}
