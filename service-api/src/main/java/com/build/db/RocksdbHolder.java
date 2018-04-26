package com.build.db;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

public class RocksdbHolder {

    public RocksDB getRocksDB() {
        return rocksDB;
    }

    public void create() {
        try {
            if( this.rocksDB == null) {
                RocksDB.loadLibrary();
                this.rocksDB = RocksDB.open("/home/centin/data");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    private RocksDB rocksDB = null;

    public static final String REGISTER_ROOT_PATH = "/mall";

}
