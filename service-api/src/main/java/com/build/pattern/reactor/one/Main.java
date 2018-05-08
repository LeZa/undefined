package com.build.pattern.reactor.one;

import java.io.*;
import java.nio.channels.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(9856);
                reactor.run();
       /*Acceptor  acceptor = new Acceptor(reactor);
       acceptor.run();*/
    }
}
