package org.okraAx.room.module.chess;

/**
 * @author TinyZ
 * @date 2017-02-24.
 */
public interface PyChessCallback {

    void callbackShowRoomStatus();
    void callbackCreateRoom();
    void callbackEnterRoom();
    void callbackExitRoom();
    void callbackGetReady();
    void callbackMoveChess();



}
