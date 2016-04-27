package com.lj.kernel.gate.command;

import com.lj.kernel.gate.command.impl.CHAT;
import com.lj.kernel.gate.command.impl.CHESS_ROUTE;
import com.lj.kernel.gate.command.impl.GATE_AUTH;
import com.lj.kernel.gate.command.impl.GATE_ROUTE;
import org.ogcs.app.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/28
 */
public enum Commands {

    INSTANCE;

    private final HashMap<Integer, Command> GAME_COMMAND_MAP;

    private static final int[] NON_AUTH_COMMAND = new int[]{1, 2, 1000, 1001, 1002};

    Commands() {
        GAME_COMMAND_MAP = new HashMap<>();

        // Gate
        GAME_COMMAND_MAP.put(10000, new CHAT());
        GAME_COMMAND_MAP.put(10001, new GATE_AUTH());
        GAME_COMMAND_MAP.put(10002, new GATE_ROUTE());
        // 象棋
        GAME_COMMAND_MAP.put(10003, new CHESS_ROUTE());

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
