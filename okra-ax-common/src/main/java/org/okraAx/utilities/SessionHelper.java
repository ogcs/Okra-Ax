package org.okraAx.utilities;

import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.okraAx.internal.v3.FySession;

/**
 * @author TinyZ.
 * @version 2017.03.16
 */
public final class SessionHelper {

    private static final ThreadLocal<Session> THREAD_LOCAL = new ThreadLocal<>();

    @SuppressWarnings("unchecked")
    public static <T extends Session> T currentSession() {
        return (T) THREAD_LOCAL.get();
    }

    public static void setSession(Session session) {
        THREAD_LOCAL.set(session);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Connector> T curPlayer() {
        FySession session = currentSession();
        if (session == null) return null;
        return (T) session.getConnector();
    }
}
