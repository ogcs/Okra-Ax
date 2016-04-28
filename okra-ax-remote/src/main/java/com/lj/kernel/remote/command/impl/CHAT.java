package com.lj.kernel.remote.command.impl;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.GpbReplys;
import com.lj.kernel.ax.core.ConnectorManager;
import com.lj.kernel.gpb.generated.Chat;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.remote.command.RemoteCommand;
import org.ogcs.app.AppContext;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class CHAT extends RemoteCommand {

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);

    @Override
    public void execute(Session session, Inbound inbound) throws Exception {
        Chat.ReqChat reqChat = Chat.ReqChat.parseFrom(inbound.getData());

        Chat.PushChat pushChat = Chat.PushChat.newBuilder()
                .setName(reqChat.getName())
                .setTarget(reqChat.getTarget())
                .setUid(reqChat.getUid())
                .setContent(reqChat.getContent()).build();
        // TODO: 无目标 - 全频道发言
        if (reqChat.getTarget() < 0) {
            connectorManager.pushAll(GpbReplys.outbound(GpbReplys.response(inbound.getId(), pushChat), reqChat.getUid(), reqChat.getTarget()));
        } else {
            // TODO: 获取指定的session
            long index = reqChat.getTarget() % 2;
            connectorManager.pushById(GpbReplys.outbound(GpbReplys.response(inbound.getId(), pushChat), reqChat.getUid(), reqChat.getTarget()), "Gate" + index);
        }
    }
}
