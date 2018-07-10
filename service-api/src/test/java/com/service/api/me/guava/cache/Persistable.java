package com.service.api.me.guava.cache;

/**
 * <p>可持久化的</p>
 */
public interface Persistable<K, V> {
    V load(K k) throws Exception;
}
