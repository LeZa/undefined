package com.build.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiServer {

    /**
     * @Description  rmi test
     * @param sck
     * @throws RemoteException
     * @throws MalformedURLException
     */
    public static void main( String sck[] )
            throws RemoteException, MalformedURLException {
        int port = 7863;
        String url = "rmi://192.168.89.54:7863/dea";
        LocateRegistry.createRegistry(port);
       /* RocksdbService rocksdbService =  new RocksdbServiceImpl();
        rocksdbService.create();
        Naming.rebind(url,rocksdbService);*/
       Naming.rebind(url,new RocksdbServiceImpl());
    }
}
