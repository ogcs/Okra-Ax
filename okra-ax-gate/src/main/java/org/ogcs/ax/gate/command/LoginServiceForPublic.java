package org.ogcs.ax.gate.command;

import org.ogcs.ax.gate.User;
import org.ogcs.app.Session;
import org.ogcs.ax.gpb.LogicPublicService.OnConnectAuth;

/**
 * @author TinyZ
 * @date 2016-10-15.
 */
public class LoginServiceForPublic {


    public void sayHello(Session session, OnConnectAuth auth) {
        User user = (User) session.getConnector();



//        user.id()
//        session.writeAndFlush();
    }

}
