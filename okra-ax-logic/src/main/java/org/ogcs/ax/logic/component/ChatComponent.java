package org.ogcs.ax.logic.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.GpbReplys;
import org.ogcs.app.AppContext;
import org.ogcs.app.Session;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.ogcs.ax.component.manager.ConnectorManager;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.gpb3.AxAnyProto;
import org.ogcs.ax.gpb3.AxAnyProto.AxAny;
import org.ogcs.ax.gpb3.GpbD;
import org.ogcs.ax.gpb3.LogicPublicProto;
import org.ogcs.ax.gpb3.LogicPublicProto.PushChatCnt;
import org.ogcs.ax.logic.impl.Modules;
import org.ogcs.ax.logic.impl.User;
import org.ogcs.ax.utilities.AxReplys;

/**
 * @author TinyZ
 * @date 2016-10-23.
 */
public enum ChatComponent {

    INSTANCE;

    private final static Logger LOG = LogManager.getLogger(ChatComponent.class);
    private AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
    private ConnectorManager sessions = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);

    public void onChat(Session session, GpbD.Request request, LogicPublicProto.ReqChat reqChat) {
        User user = (User) session.getConnector();
        if (reqChat.getRid() > 0) {
            AxInnerClient remote = components.getByHash(Modules.MODULE_CHAT, String.valueOf(reqChat.getTarget()));
            if (remote == null) {
                return;
            }
            int method = (int)(request.getData().getKey()) + 10000;// TODO: command  = c + 10000
            remote.session().writeAndFlush(
                    AxReplys.axInbound(user.id(), request.getId(), method, request.getData())
            );
        } else { // 全局私聊
            AxAny any = AxAny.newBuilder()
                    .setKey(100000)
                    .setValue(PushChatCnt.newBuilder()
                            .setUid(reqChat.getTarget())
                            .setName(reqChat.getName())
                            .setContent(reqChat.getContent())
                            .setTarget(reqChat.getTarget())
                            .build()
                            .toByteString())
                    .build();
            GpbD.Push build = GpbD.Push.newBuilder()
                    .setMsg(any)
                    .build();
            if (reqChat.getTarget() > 0) {
                // TODO: 根据私聊对象的uid路由 - 获取对方所在的gate   - 相同服务器则直接推送
                AxInnerClient gate = components.getByHash(Modules.MODULE_GATE, String.valueOf(reqChat.getTarget()));
                if (gate == null) {
                    return;
                }
                gate.session().writeAndFlush(
                        AxReplys.axOutbound(request.getId(),
                                GpbReplys.response(request.getId(), build)
                                , reqChat.getTarget())
                );
            } else {
//                components.get()

            }
        }
    }


}
