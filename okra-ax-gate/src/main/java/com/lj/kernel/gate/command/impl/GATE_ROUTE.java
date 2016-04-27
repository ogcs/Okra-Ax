package com.lj.kernel.gate.command.impl;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.gate.RemoteManager;
import com.lj.kernel.gpb.generated.GpbD.Request;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class GATE_ROUTE implements Command<Session, Request> {

    private RemoteManager remoteManager = (RemoteManager) AppContext.getBean(SpringContext.MANAGER_REMOTE);

    @Override
    public void execute(Session session, Request request) throws Exception {
//        G2RClient g2RClient1 = remoteManager.get(String.valueOf(request.getApi()), request.getKey());
//        if (g2RClient1 == null) {
//
//            return;
//        }
//        Channel channel = g2RClient1.client(); // TODO: 发送消息保护
//        if (channel.isWritable()) {
//            channel.writeAndFlush(
//                    Unpooled.wrappedBuffer(request.getData().toByteArray())
//            );
//        }
    }
}
