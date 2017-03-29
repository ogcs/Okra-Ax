package org.okraAx.room.fy;

/**
 * @author TinyZ.
 * @version 2017.03.27
 */
public final class RoomManager {



    public void start() {
        //  1. 加载配置
        //  2. 建立组件间连接
        //  3. bind端口，对外公开服务


        LogicClient logic = new LogicClient("", 9005);
        logic.start();
        //
        logic.logicClient().registerChannel();



    }

    public void stop() {

    }



}
