//package org.okraAx.room.remote.command;
//
//import org.okraAx.room.remote.RemoteCommand;
//import org.ogcs.gpb.generated.GpbChess.ReqChessJoin;
//import org.ogcs.gpb.generated.GpbChess.ResChessJoin;
//import org.okraAx.room.module.chess.Chessboard;
//import org.ogcs.app.Session;
//import org.okraAx.internal.inner.AxConnector;
//import org.okraAx.utilities.AxReplys;
//import org.okraAx.v3.OkraAx;
//
///**
// * @author : TinyZ.
// * @email : ogcs_tinyz@outlook.com
// * @date : 2016/4/14
// */
//public class CHESS_JOIN extends RemoteCommand {
//
//    @Override
//    public void execute(Session session, OkraAx.AxInbound inbound) throws Exception {
//        AxConnector axConnector = (AxConnector) session.getConnector();
//
//        ReqChessJoin reqChessJoin = ReqChessJoin.parseFrom(inbound.getData());
//
//        Chessboard room = (Chessboard) roomManager.getByUid(inbound.getSource());
//        if (room == null) {
//            room = new Chessboard(reqChessJoin.getRoomId());
//            roomManager.put(inbound.getSource(), room);
//        }
//        room.enter(axConnector.id(), inbound.getSource());
//
//        session.writeAndFlush(
//                AxReplys.axOutbound(inbound.getRid(),
//                        ResChessJoin.newBuilder()
//                                .setSide(room.index(inbound.getSource()))
//                                .build(),
//                        inbound.getSource()
//                )
//        );
//        if (room.players().size() >= 2) {
//            room.init();
//        }
//    }
//}
