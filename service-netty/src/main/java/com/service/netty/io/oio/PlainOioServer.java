package com.service.netty.io.oio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class PlainOioServer {

    public  void serve( int port ) throws IOException {
         final ServerSocket serverSocket
                    = new ServerSocket( port );
                for(;;){
                    final Socket socket = serverSocket.accept();
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            OutputStream out;
                            try {
                                out = socket.getOutputStream();
                                out.write("Hi! 6020 \r\n".getBytes(Charset.forName("UTF-8")));
                                out.flush();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                            finally{
//                                try {
//                                    serverSocket.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
                        }
                    }).start();
                }
    }


    public static void main( String sck[] ){
        try {
            PlainOioServer plainOioServer = new PlainOioServer();
            plainOioServer.serve(6020);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
