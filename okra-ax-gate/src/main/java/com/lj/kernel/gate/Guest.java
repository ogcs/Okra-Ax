package com.lj.kernel.gate;

import com.lj.kernel.gpb.generated.GpbRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.ogcs.ax.component.SpringContext;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.ogcs.ax.component.manager.ConnectorManager;

/**
 * 游客. 仅用于游客模式.
 * 默认一切登录用户都为游客，完成登录授权之后切换为正式用户
 */
public class Guest implements Connector {

    private static final Logger LOG = LogManager.getLogger(Guest.class);

    protected AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
    protected ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    protected Session session;
    protected long uid;    //   用户的唯一ID
    protected String module;  //  房间模块
    protected long roomId;   //  房间id

    public Guest(Session session, long uid) {
        this.session = session;
        this.uid = uid;
    }

    public long id() {
        return uid;
    }

    @Override
    public boolean isConnected() {
        return session != null && session.isOnline();
    }

    @Override
    public Session session() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void disconnect() {
        exitRoom();
        connectorManager.remove(session);
        LOG.info("Session : {} disconnect.", session.toString());
    }

    /**
     * Enter room
     *
     * @param module module
     * @param roomId the unique room id.
     */
    public void enterRoom(String module, long roomId) {
        this.module = module;
        this.roomId = roomId;
    }

    /**
     * Exit room.
     */
    public void exitRoom() {
        if (module == null || module.equals("") || roomId <= 0) {
            return;
        }
        AxInnerClient client = components.getByHash(module, String.valueOf(roomId));
        if (client != null) {
            client.push(uid, 21000,
                    GpbRoom.ReqExit.newBuilder()
                            .setModule(1)// module
                            .setRoomId(roomId)
                            .build().toByteString()
            );
            this.module = null;
            this.roomId = 0;
        }
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
}
