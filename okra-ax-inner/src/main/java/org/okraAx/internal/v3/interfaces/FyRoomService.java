package org.okraAx.internal.v3.interfaces;

import org.ogcs.app.Session;

/**
 * @author TinyZ
 * @date 2017-03-01.
 */
public interface FyRoomService<S extends Session> {

    void ping(S session);
    void onCreateRoom(S session);
    void onEnterRoom(S session, int roomId, int seat, long uid, String name);
    void onExitRoom(S session);
    void onShowHall(S session);
    void onGetReady(S session, long uid, boolean ready);
    void onGameStart(S session);
    void onGameEnd(S session);
    void onChat(S session);









}
