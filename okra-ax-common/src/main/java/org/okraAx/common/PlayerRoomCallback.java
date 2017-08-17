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












}
