package org.okraAx.common.modules;

/**
 * @author TinyZ.
 * @version 2017.03.16
 */
public interface FyChessService {

    void onShowRoomStatus();

    void onMoveChess(int fromX, int fromY, int toX, int toY);

}
