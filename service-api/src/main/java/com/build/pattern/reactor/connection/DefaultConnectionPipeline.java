package com.build.pattern.reactor.connection;

import com.build.pattern.reactor.common.*;
import com.build.pattern.reactor.handler.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultConnectionPipeline implements ConnectionPipeline {

    private Connection connection;
    
    private Map<String, ConnectionHandler> handlerMap = new LinkedHashMap<>();

    public DefaultConnectionPipeline(Connection connection) {
        Objects.requireNonNull(connection);
        this.connection = connection;
    }

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public synchronized ConnectionPipeline add(String name, ConnectionHandler handler) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(handler);
        handlerMap.put(name, handler);
        return this;
    }

    @Override
    public synchronized ConnectionPipeline remove(String name) {
        Objects.requireNonNull(name);
        handlerMap.remove(name);
        return this;
    }

    @Override
    public ConnectionPipeline fireRegistered() {
        handlerMap.forEach((name, handler) -> {
            System.out.println("handler {} begin to call register()"+name);
            try {
                handler.connectionRegistered(this);
            }
            catch (Exception e) {
                throw new ReactorException(e);
            }
        });
        return this;
    }

    @Override
    public ConnectionPipeline fireUnregistered() {
        return null;
    }

    @Override
    public ConnectionPipeline fireRead(Object msg) {
        handlerMap.forEach((name, handler) -> {
            System.out.println("handler {} begin to call read()"+name);
            try {
                handler.connectionRead(this, msg);
            }
            catch (Exception e) {
                throw new ReactorException(e);
            }
        });
        return this;
    }

    @Override
    public ConnectionPipeline fireWrite(Object msg) {
        handlerMap.forEach((name, handler) -> {
            System.out.println("handler {} begin to call write()"+name);
            try {
                handler.write(this, msg);
            }catch (Exception e) {
                throw new ReactorException(e);
            }
        });
        return this;
    }

    @Override
    public ConnectionPipeline flush() {
        return null;
    }
}
