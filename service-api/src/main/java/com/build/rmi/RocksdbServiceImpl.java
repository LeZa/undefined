package com.build.rmi;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RocksdbServiceImpl
    extends UnicastRemoteObject implements  RocksdbService{

    private RocksDB  rocksDB;

    public RocksdbServiceImpl() throws RemoteException {
        try {
            this.rocksDB = RocksDB.open("/home/centin/data");
        } catch (RocksDBException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] getString( String key) throws RemoteException{
        try {
            return this.rocksDB.get(key.getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setVoid( String key,String val ) throws  RemoteException{
        try {
            this.rocksDB.put(key.getBytes(),val.getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public  String  saySth(String str) throws RemoteException{
            return "input"+str;
    }
   /* @Override
    public void create() throws RemoteException {
        try {
           this.rocksDB = RocksDB.open("/home/centin/data");
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RocksDB getRocksDB() throws  RemoteException{
        return this.rocksDB;
    }*/
}
