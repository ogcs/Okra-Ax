package org.ogcs.ax.gate;

import org.ogcs.app.AppContext;
import org.ogcs.app.Session;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.ogcs.ax.component.manager.ConnectorManager;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.gate.command.inner.LOGIN_AUTH;
import org.ogcs.app.Command;
import org.ogcs.ax.component.inner.AxConsole;
import org.ogcs.ax.gpb3.GpbD;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关的控制台.
 * 用于注册和解析Command.
 *
 * <p>使用Spring框架注入的方式注册Command</p>
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/28
 */
public final class GateConsole {

    private Map<Integer, Command> console = new HashMap<>();

    private static final int[] NON_AUTH_COMMAND = new int[]{1, 2, 1000, 1001, 1002};

//    GateConsole() {
//        // Gate
//        console.put(10000, new CHAT());
//        console.put(10001, new GATE_AUTH());
//        // 房间
//        console.put(10003, new ROOM_ROUTE());
//        console.put(10004, new ROOM_ENTER());
//
//        console.put(10005, new ROOM_HALL());
//
//        console.put(10006, new GUEST_LOGIN());
//    }

    public void initialize() {
        // 注册Ax内部消息
        try {
            AxConsole.INSTANCE.register(1001, new LOGIN_AUTH());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the command instance.
     */
    public Command interpretCommand(int cmd) throws Exception {
        if (console.containsKey(cmd)) {
            return console.get(cmd);
        } else {
            throw new Exception("Unknown command : " + cmd);
        }
    }

    public boolean isCmdWithoutAuth(int cmd) {
        for (Integer allowed : NON_AUTH_COMMAND) {
            if (allowed == cmd) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, Command> getConsole() {
        return console;
    }

    public void setConsole(Map<Integer, Command> console) {
        this.console = console;
    }

    /**
     * Get the installed commands
     */
    public Map<Integer, Command> getCommandMap() {
        return console;
    }

}
