package org.okraAx.utilities;

import org.ogcs.app.Connector;
import org.ogcs.app.Session;

/**
 * @author TinyZ.
 * @version 2017.03.16
 */
public final class SessionHelper {

    private static final ThreadLocal<Session> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * @param <T> the special session class.
     * @return return the session that call the produce.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Session> T currentSession() {
        return (T) THREAD_LOCAL.get();
    }

    public static void setSession(Session session) {
        THREAD_LOCAL.set(session);
    }

    /**
     * server create an {@link Connector} instance to manage player's data and channel,
     * after the server verify player's auth success,
     *
     * @param <T> the special connector class.
     * @return return the connector instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Connector> T curPlayer() {
        Session session = currentSession();
        if (session == null) return null;
        return (T) session.getConnector();
    }
}
