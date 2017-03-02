//package org.okraAx.room.module.chat;
//
//
//
//import org.okraAx.room.module.Room;
//
//import java.util.Set;
//
///**
// * @author : TinyZ.
// * @email : ogcs_tinyz@outlook.com
// * @date : 2016/4/21
// */
//public class ChatRoom implements Room {
//
//    private long id;
//    private Set<Long> users;
//
//    public ChatRoom(long id) {
//        this.id = id;
//    }
//
//    @Override
//    public long id() {
//        return id;
//    }
//
//    @Override
//    public int type() {
//        return 0;
//    }
//
//    @Override
//    public boolean isFully() {
//        return false;
//    }
//
//    @Override
//    public void enter(String gateId, long uid){
//    }
//
//    @Override
//    public void init() {
//
//    }
//
//    @Override
//    public Set<Long> players() {
//        return null;
//    }
//
//    @Override
//    public void onExit(Long user) {
//
//    }
//
//    @Override
//    public void onDestroy() {
//
//    }
//}
