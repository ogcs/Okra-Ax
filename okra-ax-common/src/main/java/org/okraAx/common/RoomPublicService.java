package org.okraAx.common;

import org.okraAx.v3.beans.roomPub.MsgOnChat;

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
     * 玩家连接Room.
     * <p>Room组件根据安全码(security)向logic组件请求校验和获取用户信息. 校验失败直接断开玩家的连接.</p>
     */
    void onPlayerConnect(long uid, long security);
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

    void onGetReady(boolean ready);

    void onChat(MsgOnChat chat);
}
