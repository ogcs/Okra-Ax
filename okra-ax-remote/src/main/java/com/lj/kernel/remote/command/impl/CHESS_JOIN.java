package com.lj.kernel.remote.command.impl;

import com.lj.kernel.ax.GpbReplys;
import com.lj.kernel.ax.remote.GateNode;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.message.GpbChess;
import com.lj.kernel.gpb.generated.message.GpbChess.ReqChessJoin;
import com.lj.kernel.module.chess.Chessboard;
import com.lj.kernel.remote.command.RemoteCommand;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public class CHESS_JOIN extends RemoteCommand {

    @Override
    public void execute(Session session, Inbound inbound) throws Exception {
        GateNode gateNode = (GateNode) session.getConnector();

        ReqChessJoin reqChessJoin = ReqChessJoin.parseFrom(inbound.getData());
        Chessboard room = (Chessboard) roomManager.getByUid(inbound.getUid());
        if (room == null) {
            room = new Chessboard(reqChessJoin.getRoom());
            roomManager.put(inbound.getUid(), room);
        }
        room.enter(gateNode.getGateId(), inbound.getUid());

        session.writeAndFlush(
                GpbReplys.outbound(
                        GpbReplys.response(inbound.getId(), GpbChess.ResChessJoin.newBuilder()
                                .setSide(room.index(inbound.getUid()))
                                .build()),
                        inbound.getUid()
                )
        );
        if (room.players().size() >= 2) {
            room.init();
        }
    }
}
