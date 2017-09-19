package org.okraAx.room.module;

import org.okraAx.room.bean.RoomInfo;

/**
 * 游戏玩法.
 * <pre>
 *     1. 按照指定的规则初始化房间中玩家数据
 *     2. 指定和限制玩法允许参与的玩家数
 *     3. 指定胜负判定条件
 * </pre>
 *
 * @author TinyZ.
 * @version 2017.08.09
 */
public interface GameRule {

    /**
     * 根据规则初始化房间
     */
    void initGameRoom(RoomInfo bean);

    /**
     * 状态.
     * <pre>
     *     1. 未开始
     *     2. 游戏中
     * </pre>
     */
    int status();

    /**
     * 是否已经满员
     */
    boolean isFully();

    /**
     * 检查是否满足游戏结束条件.
     */
    boolean isGameEnd();

}
