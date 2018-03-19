package com.service.netty.io.oio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;

public class PlainOioClient{

    public static void main( String sck[] ){
        Socket  socket = null;
        Socket  socket1 = null;
//        int port = 6030;//NettyNio
        int port = 6010;//NettyOio
//        int port = 6020;//java.net.Oio
        try {
            System.out.println(Calendar.getInstance().getTimeInMillis());
            for(;;) {
                socket = new Socket(InetAddress.getLocalHost(), port);
                socket1 = new Socket(InetAddress.getLocalHost(), port);
                String readStr = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                String readStr1 = new BufferedReader(new InputStreamReader(socket1.getInputStream())).readLine();
                System.out.println("socket Client读到：" + readStr);
                System.out.println("socket.... Client读到：" + readStr1);
                socket.close();
                socket1.close();
            }
        } catch (IOException e) {
            System.out.println(Calendar.getInstance().getTimeInMillis());
            e.printStackTrace();
        }
    }
}
