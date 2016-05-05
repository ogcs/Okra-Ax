package com.lj.kernel.gate;

import org.ogcs.app.AppContext;
import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.ogcs.ax.component.SpringContext;
import org.ogcs.ax.component.manager.ConnectorManager;

public class User implements Connector {

//    private RoomManager roomManager = (RoomManager) AppContext.getBean(SpringContext.MODULE_ROOM_MANAGER);
    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);

    private Session session;
    private Long id;//   uid
    private long roomId;   //  角色所在房间

    //    private MemAccount memAccount;
//    public User(MemAccount memAccount) {
//        this.id = memAccount.getUid();
//        this.memAccount = memAccount;
//    }
//    public User(Session session, MemAccount memAccount) {
//        this.session = session;
//        this.memAccount = memAccount;
//        this.id = memAccount.getUid();
//    }

    public long id() {
        return id;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    @Deprecated
    public User(Session session) {
        this.session = session;
    }

    @Deprecated
    public User(Session session, long uid) {
        this.session = session;
        this.id = uid;
    }

//    public MemAccount getMemAccount() {
//        return memAccount;
//    }

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
//        roomManager.exit(this);
        connectorManager.remove(session);

        if (roomId > 0) {   //  退出房间

        }

        System.out.println("离线:" + session.toString());
    }
}
