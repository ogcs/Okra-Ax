package com.lj.kernel.remote.command;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.module.RoomManager;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public abstract class RemoteCommand implements Command<Session, Inbound> {

    // Service
    protected RoomManager roomManager = (RoomManager) AppContext.getBean(SpringContext.MODULE_ROOM_MANAGER);

    // Mapper
//    protected AccountMapper accountMapper = (AccountMapper) AppContext.getBean(SpringContext.MAPPER_ACCOUNT);


}
