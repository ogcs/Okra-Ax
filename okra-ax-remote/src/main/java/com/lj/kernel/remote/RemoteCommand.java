package com.lj.kernel.remote;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.ConnectorManager;
import com.lj.kernel.gpb.OkraAx;
import com.lj.kernel.gpb.OkraAx.AxInbound;
import com.lj.kernel.module.RoomManager;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public abstract class RemoteCommand implements Command<Session, AxInbound> {

    // Service
    protected RoomManager roomManager = (RoomManager) AppContext.getBean(SpringContext.MODULE_ROOM_MANAGER);
    protected ConnectorManager sessions = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);

    // Mapper
//    protected AccountMapper accountMapper = (AccountMapper) AppContext.getBean(SpringContext.MAPPER_ACCOUNT);


}
