package com.lj.kernel.ax.inner;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.ConnectorManager;
import com.lj.kernel.gpb.OkraAx.AxInbound;
import com.lj.kernel.gpb.OkraAx.AxReqAuth;
import io.netty.channel.ChannelFutureListener;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/28
 */
public class INNER_AUTH implements Command<Session, AxInbound> {

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);

    @Override
    public void execute(Session session, AxInbound axInbound) throws Exception {
        AxReqAuth axReqAuth = AxReqAuth.parseFrom(axInbound.getData());
        if (!axReqAuth.getKey().equals("ABCD")) {
            session.writeAndFlush(AxReplys.error(axInbound.getRid(), -91), ChannelFutureListener.CLOSE);// 访问授权校验失败 - 断开连接
            return;
        }

        AxConnector axConnector = new AxConnector(axReqAuth.getSource(), session);
        session.setConnector(axConnector);

        connectorManager.put(axConnector.id(), axConnector);
        System.out.println("节点注册成功：" + axConnector.id());
    }
}
