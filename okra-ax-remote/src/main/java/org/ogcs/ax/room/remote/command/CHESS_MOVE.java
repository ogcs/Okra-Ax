package org.ogcs.ax.room.remote.command;

import org.ogcs.ax.room.module.Room;
import org.ogcs.ax.room.remote.RemoteCommand;
import org.ogcs.gpb.generated.GpbChess.ReqChessMove;
import org.ogcs.gpb.generated.GpbChess.ResChessMove;
import org.ogcs.ax.room.module.chess.Chessboard;
import org.ogcs.app.Session;
import org.ogcs.ax.utilities.AxReplys;
import org.ogcs.ax.gpb3.OkraAx.AxInbound;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public class CHESS_MOVE extends RemoteCommand {

    @Override
    public void execute(Session session, AxInbound inbound) throws Exception {
        Room room = roomManager.getByUid(inbound.getSource());
        if (room == null || !(room instanceof Chessboard)) {
            session.writeAndFlush(
                    AxReplys.error(inbound.getRid(), -100)
            );
            return;
        }
        Chessboard chessboard = (Chessboard) room;
        ReqChessMove reqChessMove = ReqChessMove.parseFrom(inbound.getData());
        ResChessMove.Builder builder = ResChessMove.newBuilder();
        if (chessboard.isOperable(inbound.getSource())) {
            builder.setMovable(chessboard.move(reqChessMove.getFromX(), reqChessMove.getFromY(), reqChessMove.getToX(), reqChessMove.getToY()));
        } else {
            builder.setMovable(false);
        }
        session.writeAndFlush(
                AxReplys.axOutbound(inbound.getRid(),
                        builder.build(),
                        inbound.getSource()
                )
        );
    }
}
