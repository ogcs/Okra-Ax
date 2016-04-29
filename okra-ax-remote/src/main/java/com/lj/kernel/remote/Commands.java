package com.lj.kernel.remote;

import com.lj.kernel.ax.inner.AxConsole;
import com.lj.kernel.remote.command.CHAT;
import com.lj.kernel.remote.command.CHESS_JOIN;
import com.lj.kernel.remote.command.CHESS_MOVE;
import com.lj.kernel.remote.command.CHESS_ROOM_LIST;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/28
 */
public final class Commands {

    public static void register(){
        try {
            AxConsole.INSTANCE.register(20000, new CHAT());
            AxConsole.INSTANCE.register(20001, new CHESS_JOIN());
            AxConsole.INSTANCE.register(20002, new CHESS_MOVE());
            AxConsole.INSTANCE.register(20003, new CHESS_ROOM_LIST());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
