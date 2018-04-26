package com.build.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RocksdbService
    extends Remote{

    String saySth( String str ) throws RemoteException;

    byte[] getString(String key) throws  RemoteException;

    void setVoid(String key,String val) throws RemoteException;

  /*  void create() throws RemoteException;

    RocksDB getRocksDB() throws RemoteException;*/

}
