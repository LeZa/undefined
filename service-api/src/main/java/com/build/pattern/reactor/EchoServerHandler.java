package com.build.pattern.reactor;

import com.build.pattern.reactor.connection.*;
import com.build.pattern.reactor.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class EchoServerHandler extends AbstractConnectionHandler {

    @Override
    public void connectionRead(ConnectionPipeline pipeline, Object msg) throws Exception {
        super.connectionRead(pipeline, msg);
        ByteBuffer buffer = (ByteBuffer)msg;
        pipeline.connection().channel().write(buffer);
        System.out.println("EchoServerHandler connectionRead ...");
    }

    @Override
    public void connectionRegistered(ConnectionPipeline pipeline) throws Exception {
        super.connectionRegistered(pipeline);
        System.out.println("EchoServerHandler register ...");
    }

    @Override
    public void write(ConnectionPipeline pipeline, Object msg) throws Exception {
        super.write(pipeline, msg);
        ByteBuffer buffer = (ByteBuffer)msg;
        pipeline.connection().channel().write(buffer);
        System.out.println("EchoServerHandler write ...");
    }
}
