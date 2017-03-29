package org.okraAx.common;

import org.ogcs.app.Procedure;

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
    @Procedure
    void ping();

    /**
     * 玩家连接Room.
     * <p>Room组件根据安全码(security)向logic组件请求校验和获取用户信息. 校验失败直接断开玩家的连接.</p>
     */
    @Procedure
    void onPlayerConnect(long security);

    /**
     * 创建房间
     */
    @Procedure
    void onCreateRoom();

    /**
     * 进入房间
     */
    @Procedure
    void onEnterRoom(int roomId, int seat, long uid, String name);

    /**
     * 退出房间
     */
    @Procedure
    void onExitRoom();

    @Procedure
    void onShowHall();

    @Procedure
    void onGetReady(long uid, boolean ready);

    @Procedure
    void onGameStart();

    @Procedure
    void onGameEnd();

    @Procedure
    void onChat();
}
