package org.okraAx.common;

/**
 * @author TinyZ.
 * @version 2017.03.12
 */
public interface PlayerRoomCallback {

    void pong();

    void callbackEnterTable(int ret);

    void callbackShowRoomList();

    void changeRoomStatus();

    void changePlayerStatus();

    void callbackJoinRoom(int ret, long uid, String name, int figure);

    void callbackExitRoom(int ret, long uid);

    void callbackChangeUserStatus(int ret, long uid);

    void callbackChangeRoomStatus(int ret);


    void callbackOnEnter(int ret, Object info);

    void callbackOnReady(int ret, long uid);

    void callbackOnExit(long uid);

    void callbackOnGameEnd(int side);

    void callbackOnPutDownChess(int ret, int side, int x, int y);

    //  notify

    void callbackOnMoveChess(int ret, Integer fromX, Integer fromY, Integer toX, Integer toY);

    void callbackOnGameStart();

}
