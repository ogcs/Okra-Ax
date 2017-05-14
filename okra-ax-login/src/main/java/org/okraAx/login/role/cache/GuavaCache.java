package org.okraAx.login.role.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.login.server.LoginServer;
import org.okraAx.login.server.LoginUser;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author TinyZ.
 * @version 2017.05.14
 */
public class GuavaCache<K, V> {

    private static final Logger LOG = LogManager.getLogger(GuavaCache.class);
    private LoadingCache<K, V> cache;


    public V get(K key) {
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            LOG.error("[GuavaCache] get error.", e);
        }
        return null;
    }

    public V getIfPresent(K key) {
        return cache.getIfPresent(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public void refresh(K key) {
        cache.refresh(key);
    }

    public void invalidate(K key) {
        cache.invalidate(key);
    }

    public void invalidateAll() {
        cache.invalidateAll();
    }


}
