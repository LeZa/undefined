package com.build.pattern.reactor.one;

import java.io.*;
import java.nio.channels.*;

public class Acceptor  implements Runnable{

    private Reactor reactor;

    public Acceptor(Reactor reactor){
        this.reactor=reactor;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel=reactor.serverSocketChannel.accept();
            if(socketChannel!=null)//调用Handler来处理channel
                new Handler(reactor.selector, socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
