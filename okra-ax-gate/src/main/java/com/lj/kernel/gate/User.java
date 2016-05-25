package com.lj.kernel.gate;

import org.ogcs.app.Session;

public class User extends Guest {

    //    private MemAccount memAccount;
//    public User(MemAccount memAccount) {
//        this.id = memAccount.getUid();
//        this.memAccount = memAccount;
//    }
//    public User(Session session, MemAccount memAccount) {
//        this.session = session;
//        this.memAccount = memAccount;
//        this.id = memAccount.getUid();
//    }


    public User(Session session, long uid) {
        super(session, uid);
    }

//    public MemAccount getMemAccount() {
//        return memAccount;
//    }

}
