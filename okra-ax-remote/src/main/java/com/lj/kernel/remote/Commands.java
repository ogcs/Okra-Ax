package com.lj.kernel.remote;

import com.lj.kernel.remote.command.CHAT;
import com.lj.kernel.remote.command.CHESS_JOIN;
import com.lj.kernel.remote.command.CHESS_MOVE;
import com.lj.kernel.remote.command.CHESS_ROOM_LIST;
import com.lj.kernel.remote.command.room.ROOM_EXIT;
import org.ogcs.app.Command;
import org.ogcs.ax.component.inner.AxConsole;

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

            AxConsole.INSTANCE.register(21000, new ROOM_EXIT());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(int cmd, Command command) throws Exception {
        AxConsole.INSTANCE.register(cmd, command);
    }
}
