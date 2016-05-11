package com.lj.kernel.remote.command.room;

import com.lj.kernel.gpb.generated.GpbRoom.ReqExit;
import com.lj.kernel.gpb.generated.GpbRoom.ResExit;
import com.lj.kernel.module.Room;
import com.lj.kernel.remote.RemoteCommand;
import org.ogcs.app.Session;
import org.ogcs.ax.component.inner.AxReplys;
import org.ogcs.ax.gpb.OkraAx.AxInbound;

/**
 * 退出房间
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/5
 */
public class ROOM_EXIT extends RemoteCommand {
    @Override
    public void execute(Session session, AxInbound inbound) throws Exception {
        ReqExit reqExit = ReqExit.parseFrom(inbound.getData());

        Room room = roomManager.get(reqExit.getRoomId());
        if (room == null) {
            session.writeAndFlush(
                    AxReplys.error(inbound.getRid(), -1)
            );
            return;
        }
        session.writeAndFlush(
                AxReplys.axOutbound(inbound.getRid(),
                        ResExit.getDefaultInstance(),
                        inbound.getSource()
                )
        );
        // 退出房间
        room.exit(inbound.getSource());
    }
}
