package com.lj.kernel.gate.command;

import com.lj.kernel.gate.command.impl.*;
import com.lj.kernel.gate.command.inner.LOGIN_AUTH;
import org.ogcs.app.Command;
import org.ogcs.ax.component.inner.AxConsole;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/28
 */
public enum GateCommands {

    INSTANCE;

    private final HashMap<Integer, Command> GAME_COMMAND_MAP;

    private static final int[] NON_AUTH_COMMAND = new int[]{1, 2, 1000, 1001, 1002};

    GateCommands() {
        GAME_COMMAND_MAP = new HashMap<>();

        // Gate
        GAME_COMMAND_MAP.put(10000, new CHAT());
        GAME_COMMAND_MAP.put(10001, new GATE_AUTH());
        // 房间
        GAME_COMMAND_MAP.put(10003, new ROOM_ROUTE());
        GAME_COMMAND_MAP.put(10004, new ROOM_ENTER());

        GAME_COMMAND_MAP.put(10005, new ROOM_HALL());

        GAME_COMMAND_MAP.put(10006, new GUEST_LOGIN());
    }

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
        if (GAME_COMMAND_MAP.containsKey(cmd)) {
            return GAME_COMMAND_MAP.get(cmd);
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

    /**
     * Get the installed commands
     */
    public Map<Integer, Command> getCommandMap() {
        return GAME_COMMAND_MAP;
    }
}
