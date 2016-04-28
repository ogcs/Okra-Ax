//package com.lj.kernel.gate.command.impl;
//
//import com.lj.kernel.ax.SpringContext;
//import com.lj.kernel.ax.AxReplys;
//import com.lj.kernel.gate.UserManager;
//import com.lj.kernel.gate.command.AgentCommand;
//import com.lj.kernel.gpb.generated.GpbD.Inbound;
//import com.lj.kernel.gpb.generated.message.GpbUser.ReqLogin;
//import org.ogcs.app.AppContext;
//import org.ogcs.app.Command;
//import org.ogcs.app.Session;
//
//import static com.lj.kernel.gpb.generated.GpbD.Request;
//
//public class USER_LOGIN extends AgentCommand {
//
//    private UserManager userManager = (UserManager) AppContext.getBean(SpringContext.MODULE_USER_MANAGER);
//
//    @Override
//    public void execute(Session session, Request axInbound) throws Exception {
//        Request request = Request.parseFrom(axInbound.getData());
//        //
//        ReqLogin reqLogin = ReqLogin.parseFrom(request.getData());
//        User user = userManager.getByEmail(reqLogin.getAccount());
//        if (user == null) {
//            session.writeAndFlush(AxReplys.error(request.getId(), -1));
//            return;
//        }
//        MemAccount memAccount = user.getMemAccount();
//        if (!memAccount.getPsw().equals(reqLogin.getPsw())) {
//            session.writeAndFlush(AxReplys.error(request.getId(), -1));
//            return;
//        }
//        user.setSession(session);
//        session.setConnector(user);
//
//        session.writeAndFlush(AxReplys.error(request.getId(), -1));
//    }
//}
