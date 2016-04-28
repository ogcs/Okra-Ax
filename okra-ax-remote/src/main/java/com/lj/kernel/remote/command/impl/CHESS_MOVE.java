package com.lj.kernel.remote.command.impl;

import com.lj.kernel.ax.GpbReplys;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.message.GpbChess.ReqChessMove;
import com.lj.kernel.gpb.generated.message.GpbChess.ResChessMove;
import com.lj.kernel.module.chess.Chessboard;
import com.lj.kernel.module.Room;
import com.lj.kernel.remote.command.RemoteCommand;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public class CHESS_MOVE extends RemoteCommand {

    @Override
    public void execute(Session session, Inbound inbound) throws Exception {
        Room room = roomManager.getByUid(inbound.getUid());
        if (room == null || !(room instanceof Chessboard)) {
            session.writeAndFlush(
                    GpbReplys.outbound(
                            GpbReplys.error(inbound.getId(), -1), inbound.getUid()
                    )
            );
            return;
        }
        Chessboard chessboard = (Chessboard) room;
        ReqChessMove reqChessMove = ReqChessMove.parseFrom(inbound.getData());
        ResChessMove.Builder builder = ResChessMove.newBuilder();
        if (chessboard.isOperable(inbound.getUid())) {
            builder.setMovable(chessboard.move(reqChessMove.getFromX(), reqChessMove.getFromY(), reqChessMove.getToX(), reqChessMove.getToY()));
        } else {
            builder.setMovable(false);
        }
        session.writeAndFlush(GpbReplys.outbound(GpbReplys.response(inbound.getId(), builder)));
    }
}
