package org.okraAx.room.module;

import org.okraAx.room.bean.RoomInfo;

/**
 * @author TinyZ.
 * @version 2017.08.09
 */
public interface RoomFactory {

    Room newInstance(RoomInfo<?> roomInfo);

}
