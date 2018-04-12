// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
//  This source code is licensed under both the GPLv2 (found in the
//  COPYING file in the root directory) and Apache 2.0 License
//  (found in the LICENSE.Apache file in the root directory).

package com.service.api.me.db.rocksdb;

import org.junit.Test;
import org.rocksdb.WALRecoveryMode;

import static org.assertj.core.api.Assertions.assertThat;


public class WALRecoveryModeTest {

  @Test
  public void getWALRecoveryMode() {
    for (final WALRecoveryMode walRecoveryMode : WALRecoveryMode.values()) {
      assertThat(WALRecoveryMode.getWALRecoveryMode(walRecoveryMode.getValue()))
          .isEqualTo(walRecoveryMode);
    }
  }
}
