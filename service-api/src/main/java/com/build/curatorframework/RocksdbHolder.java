package com.build.curatorframework;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

public class RocksdbHolder {

    public RocksDB getRocksDB() {
        return rocksDB;
    }

    public void setRocksDB(RocksDB rocksDB) {
        this.rocksDB = rocksDB;
    }

    private RocksDB rocksDB = null;


    public void createRocksDB(){

            if( this.rocksDB != null ){
                try {
                    this.rocksDB = RocksDB.open("/home/centin/data");
                } catch (RocksDBException e) {
                    e.printStackTrace();
                }
            }
    }


    public void setVal(String key,String val){
        try {
            this.rocksDB.put(key.getBytes(),val.getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    public byte[] getVal( String key ){
        try {
            return this.rocksDB.get(key.getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
