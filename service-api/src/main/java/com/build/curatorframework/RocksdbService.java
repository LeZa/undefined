package com.build.curatorframework;

public interface RocksdbService {

    byte[] getVal( String key );

    void setVal( String key,String val );
}
