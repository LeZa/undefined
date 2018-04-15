// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
//  This source code is licensed under both the GPLv2 (found in the
//  COPYING file in the root directory) and Apache 2.0 License
//  (found in the LICENSE.Apache file in the root directory).

package com.service.api.me.db.rocksdb;

import org.junit.Test;
import org.rocksdb.Cache;
import org.rocksdb.ClockCache;
import org.rocksdb.RocksDB;

public class ClockCacheTest {

  static {
    RocksDB.loadLibrary();
  }

  @Test
  public void newClockCache() {
    final long capacity = 1000;
    final int numShardBits = 16;
    final boolean strictCapacityLimit = true;
    try(final Cache clockCache = new ClockCache(capacity,
        numShardBits, strictCapacityLimit)) {
      //no op
    }
  }
}