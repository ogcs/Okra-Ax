package org.okraAx.room.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 基础房间信息类.
 * 其他类型房间信息继承此类.
 * @author TinyZ.
 * @version 2017.06.09
 */
public class BaseRoomInfo<P extends PlayerInfo> implements Serializable {

    private static final long serialVersionUID = -6163582521900317827L;
    private long roomId; //  房间唯一ID
    private volatile int type;   //  房间类型
    private volatile int status;    //  房间状态
    private volatile P[] players;    //  玩家列表 - 固定位置






}
