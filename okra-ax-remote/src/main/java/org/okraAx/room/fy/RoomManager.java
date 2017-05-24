package org.okraAx.room.fy;

import org.okraAx.internal.v3.MtdDescUtil;
import org.okraAx.internal.v3.ConnectionEventHandler;
import org.okraAx.v3.room.beans.FyRoomMi;

/**
 * @author TinyZ.
 * @version 2017.03.27
 */
public final class RoomManager {

    private LogicClient logicClient;

    public void start() {
        //  1. 加载配置
        //  2. 建立组件间连接
        //  3. bind端口，对外公开服务

        MtdDescUtil.INSTANCE.registerMethodDesc(FyRoomMi.getDescriptor().getServices());


        logicClient = new LogicClient("", 9005, new LogicClientEventHandler());
        logicClient.start();


    }

    public void stop() {

    }


    private class LogicClientEventHandler implements ConnectionEventHandler {

        @Override
        public void connected() {
            logicClient.logicClient().registerChannel();
        }

        @Override
        public void connectFailed() {

        }

        @Override
        public void disconnected() {

        }
    }
}
