package com.build.db.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RocksdbService
    extends Remote{

    byte[] getString( String key ) throws RemoteException;

    void setVoid( String key,String val) throws  RemoteException;
}
