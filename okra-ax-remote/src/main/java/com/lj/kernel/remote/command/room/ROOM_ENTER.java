package com.lj.kernel.remote.command.room;

import com.lj.kernel.gpb.generated.GpbRoom.ReqEnter;
import com.lj.kernel.gpb.generated.GpbRoom.ResEnter;
import com.lj.kernel.module.Room;
import com.lj.kernel.module.chess.Chessboard;
import com.lj.kernel.remote.RemoteCommand;
import org.ogcs.app.Session;
import org.ogcs.ax.component.AxConnector;
import org.ogcs.ax.component.inner.AxReplys;
import org.ogcs.ax.gpb.OkraAx.AxInbound;

/**
 * 进入房间
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/10
 */
public class ROOM_ENTER extends RemoteCommand {

    @Override
    public void execute(Session session, AxInbound inbound) throws Exception {
        AxConnector connector = (AxConnector) session.getConnector();
        ReqEnter reqEnter = ReqEnter.parseFrom(inbound.getData());

        reqEnter.getModule();   //  TODO: 根据module查找
        reqEnter.getName();     //  玩家信息 - 昵称

        Room room = roomManager.get(reqEnter.getRoomId());
        if (room == null) {
            session.writeAndFlush(AxReplys.error(inbound.getRid(), -200));
            return;
        }
        if (room.isFully()) {
            session.writeAndFlush(AxReplys.error(inbound.getRid(), -201));
            return;
        }

        // Enter room
        room.enter(connector.id(), inbound.getSource());

        ResEnter.Builder builder = ResEnter.newBuilder();
        switch (reqEnter.getModule()) {
            case 1: {
                if (!(room instanceof Chessboard)) {
                    session.writeAndFlush(AxReplys.error(inbound.getRid(), -2));//房间类型错误
                }
                Chessboard room1 = (Chessboard) room;
                builder.setSide(room1.index(inbound.getSource()));
                break;
            }
            default:
                throw new IllegalStateException("Unknown module : " + reqEnter.getModule());
        }
        session.writeAndFlush(
                AxReplys.axOutbound(inbound.getRid(),
                        builder.build(),
                        inbound.getSource()
                )
        );
        if (room.isFully()) {
            room.init();
        }
    }
}
