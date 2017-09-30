package org.okraAx.room.bean;

import org.okraAx.room.module.GameRule;

import java.io.Serializable;

/**
 * 基础房间信息类.
 *
 * @author TinyZ.
 * @version 2017.06.09
 */
public class RoomInfo<P extends RemotePlayerInfo> implements Serializable {

    private static final long serialVersionUID = -6163582521900317827L;
    public long roomId; //  房间唯一ID
    public volatile int roomType;   //  房间类型
    public volatile long owner;   //  房主uid
    public volatile String title;   //  标题
    public volatile int maxPlayer;   //  最大玩家数
    public volatile String password;   //  房间密码

    public volatile P[] players;    //  玩家列表 - 固定位置
    public volatile int status;    //  房间状态
    public GameRule rules; //  玩法规则


}
