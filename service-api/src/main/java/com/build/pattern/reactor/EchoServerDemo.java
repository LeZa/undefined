package com.build.pattern.reactor;



public class EchoServerDemo {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new DefaultEventLoopGroup(2);
        Acceptor acceptor = new Acceptor(eventLoopGroup, 12345);
        acceptor.addHandler(new EchoServerHandler());
        acceptor.start();
    }
}
