package org.ogcs.ax.room.module;


import java.util.Set;

/**
 * 游戏房间
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public interface Room {

    /**
     * 房间唯一ID
     *
     * @return 唯一ID
     */
    long id();

    /**
     * 获取当前房间的类型
     *
     * @return 房间类型
     */
    int type();

    /**
     * 是否房间已满
     * @return 当房间满,返回true，否则返回false
     */
    boolean isFully();

    /**
     * 用户进入房间
     *
     * @param gateId 网关服务器id
     * @param uid 用户
     */
    void enter(String gateId, long uid);

    /**
     * 初始化房间
     */
    void init();

    /**
     * 获取房间内的用户
     *
     * @return 返回房间内的全部用户
     */
    Set<Long> players();

    /**
     * 用户退出房间
     *
     * @param uid 用户
     */
    void exit(Long uid);

    /**
     * 销毁房间
     */
    void destroy();
}
