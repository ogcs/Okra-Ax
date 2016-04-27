package com.lj.kernel.remote;

import com.lj.kernel.remote.command.impl.*;
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


        // Remote
        GAME_COMMAND_MAP.put(20000, new CHAT());
        GAME_COMMAND_MAP.put(20001, new CHESS_JOIN());
        GAME_COMMAND_MAP.put(20002, new CHESS_MOVE());
        GAME_COMMAND_MAP.put(20003, new CHESS_ROOM_LIST());

        GAME_COMMAND_MAP.put(29999, new REMOTE_AUTH());
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
