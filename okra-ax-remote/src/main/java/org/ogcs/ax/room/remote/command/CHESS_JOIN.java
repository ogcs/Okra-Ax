package org.ogcs.ax.room.remote.command;

import org.ogcs.ax.room.remote.RemoteCommand;
import org.ogcs.gpb.generated.GpbChess.ReqChessJoin;
import org.ogcs.gpb.generated.GpbChess.ResChessJoin;
import org.ogcs.ax.room.module.chess.Chessboard;
import org.ogcs.app.Session;
import org.ogcs.ax.component.inner.AxConnector;
import org.ogcs.ax.utilities.AxReplys;
import org.ogcs.ax.gpb3.OkraAx.AxInbound;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public class CHESS_JOIN extends RemoteCommand {

    @Override
    public void execute(Session session, AxInbound inbound) throws Exception {
        AxConnector axConnector = (AxConnector) session.getConnector();

        ReqChessJoin reqChessJoin = ReqChessJoin.parseFrom(inbound.getData());

        Chessboard room = (Chessboard) roomManager.getByUid(inbound.getSource());
        if (room == null) {
            room = new Chessboard(reqChessJoin.getRoomId());
            roomManager.put(inbound.getSource(), room);
        }
        room.enter(axConnector.id(), inbound.getSource());

        session.writeAndFlush(
                AxReplys.axOutbound(inbound.getRid(),
                        ResChessJoin.newBuilder()
                                .setSide(room.index(inbound.getSource()))
                                .build(),
                        inbound.getSource()
                )
        );
        if (room.players().size() >= 2) {
            room.init();
        }
    }
}
