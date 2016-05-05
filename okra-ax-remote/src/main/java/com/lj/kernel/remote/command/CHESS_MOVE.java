package com.lj.kernel.remote.command;

import com.lj.kernel.gpb.generated.GpbChess;
import com.lj.kernel.gpb.generated.GpbChess.ReqChessMove;
import com.lj.kernel.gpb.generated.GpbChess.ResChessMove;
import org.ogcs.ax.component.GpbReplys;
import org.ogcs.ax.component.inner.AxReplys;
import org.ogcs.ax.gpb.OkraAx.AxInbound;
import com.lj.kernel.module.Room;
import com.lj.kernel.module.chess.Chessboard;
import com.lj.kernel.remote.RemoteCommand;
import org.ogcs.app.Session;

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
                    AxReplys.axOutbound(inbound.getRid(),
                            GpbReplys.error(inbound.getRid(), -1), inbound.getSource()
                    )
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
                        GpbReplys.response(inbound.getRid(), builder), inbound.getSource()
                )
        );
    }
}
