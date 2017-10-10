package org.okraAx.room.module.rules;

import org.okraAx.room.fy.RemoteUser;

/**
 * 二维坐标移动
 * @author TinyZ.
 * @version 2017.09.26
 */
public interface RuleVector2DMovable {

    boolean onMoveTo(RemoteUser userInfo, int fromX, int fromY, int toX, int toY);
}
