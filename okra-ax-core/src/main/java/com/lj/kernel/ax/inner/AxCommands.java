package com.lj.kernel.ax.inner;

import org.ogcs.app.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/28
 */
public enum AxCommands {

    INSTANCE;

    private final HashMap<Integer, Command> GAME_COMMAND_MAP;

    private static final int[] NON_AUTH_COMMAND = new int[]{1, 2, 1000, 1001, 1002};

    AxCommands() {
        GAME_COMMAND_MAP = new HashMap<>();

        GAME_COMMAND_MAP.put(900, new INNER_AUTH());
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
