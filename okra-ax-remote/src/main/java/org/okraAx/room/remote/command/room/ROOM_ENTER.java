//package org.okraAx.room.remote.command.room;
//
//import org.okraAx.room.module.Room;
//import org.okraAx.room.remote.RemoteCommand;
//import org.ogcs.gpb.generated.GpbRoom.ReqEnter;
//import org.ogcs.gpb.generated.GpbRoom.ResEnter;
//import org.okraAx.room.module.chess.Chessboard;
//import org.ogcs.app.Session;
//import org.okraAx.internal.inner.AxConnector;
//import org.okraAx.utilities.AxReplys;
//import org.okraAx.v3.OkraAx;
//
///**
// * 进入房间
// *
// * @author : TinyZ.
// * @email : tinyzzh815@gmail.com
// * @date : 2016/5/10
// */
//public class ROOM_ENTER extends RemoteCommand {
//
//    @Override
//    public void execute(Session session, OkraAx.AxInbound inbound) throws Exception {
//        AxConnector connector = (AxConnector) session.getConnector();
//        ReqEnter reqEnter = ReqEnter.parseFrom(inbound.getData());
//
//        reqEnter.getModule();   //  TODO: 根据module查找
//        reqEnter.getName();     //  玩家信息 - 昵称
//
//        Room room = roomManager.get(reqEnter.getRoomId());
//        if (room == null) {
//            session.writeAndFlush(AxReplys.error(inbound.getRid(), -200));
//            return;
//        }
//        if (room.isFully()) {
//            session.writeAndFlush(AxReplys.error(inbound.getRid(), -201));
//            return;
//        }
//
//        // Enter room
//        room.onEnter(connector.id(), inbound.getSource());
//
//        ResEnter.Builder builder = ResEnter.newBuilder();
//        switch (reqEnter.getModule()) {
//            case 1: {
//                if (!(room instanceof Chessboard)) {
//                    session.writeAndFlush(AxReplys.error(inbound.getRid(), -2));//房间类型错误
//                }
//                Chessboard room1 = (Chessboard) room;
//                builder.setSide(room1.index(inbound.getSource()));
//                break;
//            }
//            default:
//                throw new IllegalStateException("Unknown module : " + reqEnter.getModule());
//        }
//        session.writeAndFlush(
//                AxReplys.axOutbound(inbound.getRid(),
//                        builder.build(),
//                        inbound.getSource()
//                )
//        );
//        if (room.isFully()) {
//            room.init();
//        }
//    }
//}
