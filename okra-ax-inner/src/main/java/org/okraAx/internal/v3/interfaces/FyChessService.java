package org.okraAx.internal.v3.interfaces;

import org.ogcs.app.Session;

/**
 * @author TinyZ
 * @date 2017-02-24.
 */
public interface FyChessService<S extends Session> {

    void onShowRoomStatus(S session);

    void onMoveChess(S session, int fromX, int fromY, int toX, int toY);

}
