package com.service.api.me.curator;

import org.rocksdb.*;

import java.io.File;
import java.io.Serializable;
import java.util.BitSet;

public class RocksDBHolder
        implements Serializable {

    public RocksDBHolder(){
        getLock();
        RocksDB.loadLibrary();
        initConfig();
        openRocksDB();
    }

    private static final String HUSKAR_ROCKSDB_DATA_FILE = "rocksdb_data_file";
    private String dataPath;


    private static final String HUSKAR_ROCKSDB_BLOCK_CACHE_SIZE = "rocksdb_block_cache_size";
    private static final int DEFAULT_ROCKSDB_BLOCK_CACHE_SIZE = 2;
    private static final String HUSKAR_ROCKSDB_BLOCK_CACHE_COMPRESSED_SIZE = "rocksdb_block_cache_compressed_size";
    private static final int DEFAULT_ROCKSDB_BLOCK_CACHE_COMPRESSED_SIZE = 1;

    private static final String ROCKSDB_WAL_DATA_PATH = "data/wal_rocksdb";
    private static final int MAX_ROCKSDB_BLOCK_CACHE_SIZE = 40;
    private static final long CACHE_SIZE_UNIT = 1024 * 1024 * 1024L; // GB

    private int blockCacheSize;
    private int blockCacheCompressedSize;

    private RocksDB rocksDB;
    private Options options;
    private ReadOptions readOptions;
    private Filter bloomFilter;
    private File lock ;
    private String id;

    private BitSet slot = new BitSet(16383);

    public RocksDBHolder(String id) {
        this.id = id;

    }

    public RocksDB getResource() {
        return rocksDB;
    }

    public ReadOptions getReadOptions() {
        return readOptions;
    }

    /**
     * 清空RocksDB
     * */
    public void flushDB() {
        closeRocksDB();
        openRocksDB();
        return ;
    }

    public void getLock() {
        File lockDir = new File(ROCKSDB_WAL_DATA_PATH+File.separator+id);
        boolean ret = lockDir.mkdirs();
        if(!ret){
            System.out.println("Rocksdb instance with id {} is exists.");
        }
    }

    public void cleanLock(){
        File lockDir = new File(ROCKSDB_WAL_DATA_PATH+File.separator+id);
        try {
            lockDir.delete();
        }catch (Exception e){
          e.printStackTrace();
        }
    }


    private void setBlockCacheSize() {
        int cacheSize = 10;
        if (cacheSize > 0 && cacheSize <= MAX_ROCKSDB_BLOCK_CACHE_SIZE) {
            blockCacheSize = cacheSize;
        } else {
            System.out.println("--> blockCacheSize is invalid, set default value");
            blockCacheSize = DEFAULT_ROCKSDB_BLOCK_CACHE_SIZE;
        }
        return ;
    }

    private void setBlockCacheCompressedSize() {
        int cacheCompressedSize = 5 ;
        if (cacheCompressedSize > 0 && cacheCompressedSize <= MAX_ROCKSDB_BLOCK_CACHE_SIZE) {
            blockCacheCompressedSize = cacheCompressedSize;
        } else {
            System.out.println("--> blockCacheCompressedSize is invalid, set default value");
            blockCacheCompressedSize = DEFAULT_ROCKSDB_BLOCK_CACHE_COMPRESSED_SIZE;
        }
        return ;
    }

    private void initConfig() {
        setBlockCacheSize();
        setBlockCacheCompressedSize();

        options = new Options();
        options
                .setCreateIfMissing(true)
                .setWriteBufferSize(64 * 1024 * 1024)
                .setMaxWriteBufferNumber(8)
                .setMinWriteBufferNumberToMerge(4)
                .setBloomLocality(4)
                .setWalDir(ROCKSDB_WAL_DATA_PATH)
                .setWalTtlSeconds(3 * 60 * 60L);

        readOptions = new ReadOptions();
        readOptions.setVerifyChecksums(false);

        dataPath = "data/rocksdb";
        File dataDir = new File(dataPath);
        if (dataDir.exists()) {

        } else {
            if (dataDir.mkdirs()) {
            }
        }
        return ;
    }

    private void openRocksDB() {
        try {
            rocksDB = RocksDB.open(options, dataPath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to startup rocksdb instance.");
        }
        return ;
    }

    void closeRocksDB() {
        if (rocksDB != null) {
            rocksDB.close();
            rocksDB = null;
        }
        cleanLock();
        return ;
    }

}
