//package com.lj.kernel.gate.command.impl;
//
//import com.lj.kernel.ax.Modules;
//import com.lj.kernel.ax.ServerProperties;
//import com.lj.kernel.ax.AxReplys;
//import com.lj.kernel.ax.gate.G2RClient;
//import com.lj.kernel.gate.User;
//import com.lj.kernel.gate.command.AgentCommand;
//import com.lj.kernel.gpb.generated.GpbD.Request;
//import com.lj.kernel.gpb.generated.message.GpbChess.ReqChessJoin;
//import org.ogcs.app.Session;
//
///**
// * @author : TinyZ.
// * @email : ogcs_tinyz@outlook.com
// * @date : 2016/4/14
// */
//public class CHESS_ENTER extends AgentCommand {
//
//    @Override
//    public void execute(Session session, Request request) throws Exception {
//        User user = (User) session.getConnector();
//        ReqChessJoin reqChessJoin = ReqChessJoin.parseFrom(request.getData());
//
//        long rid = user.getRid();
//        if (rid > 0) { //   进入旧房间
//
//        } else { // 创建新房间
//            rid = ServerProperties.id(); // 生成一个新id
//            G2RClient g2RClient = remoteManager.get(Modules.MODULE_CHESS, String.valueOf(rid));
//            if (g2RClient != null) {
//                Session session1 = g2RClient.session();
//                if (session1.isOnline()) {
//                    session1.writeAndFlush(AxReplys.inbound(user.id(), request.getId(), request.getMethod(), ));
//                }
//            }
//        }
//
//
//
//
//
//
//
//
////        Room room;
////        if (user != null) {
////            room = roomManager.get(user);
////            if (room != null) {
////                room.enter(user);
////            }
////        } else {
////            // TODO: 不推荐 - 丢失用户信息
////            user = new User(session);
////            session.setConnector(user);
////
////            room = roomManager.get(reqChessJoin.getRoom());
////            if (room == null) {
////                // 创建中国象棋房间
////                room = new Chessboard(reqChessJoin.getRoom());
////            }
////            roomManager.put(user, room);
////            room.enter(user);
////        }
////        session.writeAndFlush(AxReplys.response(request.getId(), GpbChess.ResChessJoin.newBuilder()
////                .setSide(((Chessboard) room).index(session))
////                .build()));
////        if (room.players().size() >= 2) {
////            room.init();
////        }
//    }
//}
