package com.build.rmi;

import java.rmi.Naming;

public class RmiClient {

    public static void main(String[] args) throws Exception {
        String url = "rmi://127.0.0.1:7863/dea";
        RocksdbService helloService = (RocksdbService) Naming.lookup(url);
        System.out.println( helloService.saySth("...sushile"));
        System.out.println( new String ( helloService.getString("123") ));
    }

}
