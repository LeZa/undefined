package com.service.api.me.base.dataType;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rocksdb.*;

import java.io.File;
import java.util.BitSet;

public class FileT {

    private static final int DEFAULT_ROCKSDB_BLOCK_CACHE_SIZE = 2;
    private static final int DEFAULT_ROCKSDB_BLOCK_CACHE_COMPRESSED_SIZE = 1;

    private static final String ROCKSDB_WAL_DATA_PATH = "192.168.109.32/home/weiyoukuan/battery/dataTemp/wal_rocksdb";
    private static final int MAX_ROCKSDB_BLOCK_CACHE_SIZE = 40;
    private static final long CACHE_SIZE_UNIT = 1024 * 1024 * 1024L; // GB

    private static final  String dataPath="192.168.109.32/home/weiyoukuan/battery/dataTemp/rocksdb/";

    @Test
    public void createRemoteFile() throws RocksDBException {
        BloomFilter  bloomFilter = new BloomFilter(12);
        BlockBasedTableConfig tableConfig = new BlockBasedTableConfig();
        tableConfig.setBlockCacheSize(5 * CACHE_SIZE_UNIT)
                .setBlockCacheCompressedSize(2 * CACHE_SIZE_UNIT)
                .setIndexType(IndexType.kBinarySearch)
                .setCacheIndexAndFilterBlocks(true)
                .setFilter(bloomFilter);


            Options options = new Options();
            options
                    .setCreateIfMissing(true)
                    .setWriteBufferSize(64 * 1024 * 1024)
                    .setMaxWriteBufferNumber(8)
                    .setMinWriteBufferNumberToMerge(4)
                    .setBloomLocality(4)
                    .setWalDir(ROCKSDB_WAL_DATA_PATH)
                    .setWalTtlSeconds(3 * 60 * 60L)
                    .setTableFormatConfig(tableConfig);





               RocksDB rocksDB = RocksDB.open(options, dataPath);

                rocksDB.put("key".getBytes(),"value".getBytes());



    }
}
