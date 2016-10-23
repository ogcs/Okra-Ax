package org.ogcs.ax.logic.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/5
 * @since 1.0
 */
public enum AuthUtil {

    INSTANCE;

    private Map<Long, Long> AUTHS = new ConcurrentHashMap<>();

    public void put(Long uid, Long auth) {
        AUTHS.put(uid, auth);
    }

    public boolean verifyAuth(Long uid, Long auth) {
        Long var1 = AUTHS.get(uid);
        return var1 != null && auth.equals(var1);
    }

    public void remove(Long uid) {
        AUTHS.remove(uid);
    }
}
