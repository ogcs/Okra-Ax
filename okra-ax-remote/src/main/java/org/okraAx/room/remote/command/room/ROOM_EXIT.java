//package org.okraAx.room.remote.command.room;
//
//import org.okraAx.room.module.Room;
//import org.okraAx.room.remote.RemoteCommand;
//import org.ogcs.gpb.generated.GpbRoom.ReqExit;
//import org.ogcs.gpb.generated.GpbRoom.ResExit;
//import org.ogcs.app.Session;
//import org.okraAx.utilities.AxReplys;
//import org.okraAx.v3.OkraAx;
//
///**
// * 退出房间
// *
// * @author : TinyZ.
// * @email : tinyzzh815@gmail.com
// * @date : 2016/5/5
// */
//public class ROOM_EXIT extends RemoteCommand {
//    @Override
//    public void execute(Session session, OkraAx.AxInbound inbound) throws Exception {
//        ReqExit reqExit = ReqExit.parseFrom(inbound.getData());
//
//        Room room = roomManager.get(reqExit.getRoomId());
//        if (room == null) {
//            session.writeAndFlush(
//                    AxReplys.error(inbound.getRid(), -1)
//            );
//            return;
//        }
//        session.writeAndFlush(
//                AxReplys.axOutbound(inbound.getRid(),
//                        ResExit.getDefaultInstance(),
//                        inbound.getSource()
//                )
//        );
//        // 退出房间
//        room.onExit(inbound.getSource());
//    }
//}
