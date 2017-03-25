package org.okraAx.common;

/**
 * Room public service interface for player.
 *
 * @author TinyZ
 * @version 2017.03.12
 */
public interface RoomPublicService {

    /**
     * player测试连接服务器ping值
     */
    void ping();

    /**
     * 创建房间
     */
    void onCreateRoom();

    /**
     * 进入房间
     */
    void onEnterRoom(int roomId, int seat, long uid, String name);

    /**
     * 退出房间
     */
    void onExitRoom();

    void onShowHall();

    void onGetReady(long uid, boolean ready);

    void onGameStart();

    void onGameEnd();

    void onChat();
}
