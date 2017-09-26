package org.okraAx.utilities;


import org.okraAx.internal.v3.NetSession;

/**
 * @author TinyZ.
 * @version 2017.03.16
 */
public final class NetHelper {

    private static final ThreadLocal<NetSession> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * @return return the session that call the produce.
     */
    public static NetSession session() {
        return THREAD_LOCAL.get();
    }

    public static void setSession(NetSession session) {
        THREAD_LOCAL.set(session);
    }
}
