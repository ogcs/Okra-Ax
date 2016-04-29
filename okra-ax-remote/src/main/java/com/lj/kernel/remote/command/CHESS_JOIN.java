package com.lj.kernel.remote.command;

import com.lj.kernel.ax.GpbReplys;
import com.lj.kernel.ax.inner.AxConnector;
import com.lj.kernel.ax.inner.AxReplys;
import com.lj.kernel.gpb.OkraAx.AxInbound;
import com.lj.kernel.gpb.generated.message.GpbChess;
import com.lj.kernel.gpb.generated.message.GpbChess.ReqChessJoin;
import com.lj.kernel.module.chess.Chessboard;
import com.lj.kernel.remote.RemoteCommand;
import org.ogcs.app.Session;

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
            room = new Chessboard(reqChessJoin.getRoom());
            roomManager.put(inbound.getSource(), room);
        }
        room.enter(axConnector.id(), inbound.getSource());

        session.writeAndFlush(
                AxReplys.axOutbound(inbound.getRid(),
                        GpbReplys.response(inbound.getRid(),
                                GpbChess.ResChessJoin.newBuilder()
                                        .setSide(room.index(inbound.getSource()))
                                        .build()),
                        inbound.getSource()
                )
        );
        if (room.players().size() >= 2) {
            room.init();
        }
    }
}
