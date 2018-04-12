// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
//  This source code is licensed under both the GPLv2 (found in the
//  COPYING file in the root directory) and Apache 2.0 License
//  (found in the LICENSE.Apache file in the root directory).

package com.service.api.me.db.rocksdb;

import org.junit.Test;
import org.rocksdb.CompactionOptionsFIFO;
import org.rocksdb.RocksDB;

import static org.assertj.core.api.Assertions.assertThat;

public class CompactionOptionsFIFOTest {

  static {
    RocksDB.loadLibrary();
  }

  @Test
  public void maxTableFilesSize() {
    final long size = 500 * 1024 * 1026;
    try(final CompactionOptionsFIFO opt = new CompactionOptionsFIFO()) {
      opt.setMaxTableFilesSize(size);
      assertThat(opt.maxTableFilesSize()).isEqualTo(size);
    }
  }
}
