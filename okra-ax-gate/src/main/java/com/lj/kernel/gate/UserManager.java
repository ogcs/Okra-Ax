//package com.lj.kernel.gate;
//
//import com.lj.kernel.gate.persistence.domain.MemAccount;
//import com.lj.kernel.gate.persistence.mapper.AccountMapper;
//import com.lj.kernel.utilities.RedisUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 用户管理
// *
// * @author : TinyZ.
// * @email : ogcs_tinyz@outlook.com
// * @date : 2016/3/31
// */
//public class UserManager {
//
//    @Autowired
//    private RedisUtil redisUtil;
//    @Autowired
//    private AccountMapper accountMapper;
//
//    private Map<Long, User> uidMap = new HashMap<>();
//    private Map<String, User> emailMap = new HashMap<>();
//    private Map<Long, User> phoneMap = new HashMap<>();
//
//    private static String keyUser(Long uid) {
//        return "r:user:" + uid;
//    }
//
//    private static String keyEmail(String email) {
//        return "r:user:email:" + email;
//    }
//
//    private static String keyPhone(Long phone) {
//        return "r:user:phone:" + phone;
//    }
//
//    public User get(long uid) {
//        if (uidMap.containsKey(uid)) {
//            return uidMap.get(uid);
//        } else {
//            User user = null;
//            String key = keyUser(uid);
//            MemAccount memAccount = redisUtil.objGet(key, MemAccount.class);
//            if (memAccount != null) {
//                user = new User(memAccount);
//                cacheUser(user);
//                uidMap.put(memAccount.getUid(), user);
//            } else {
//                memAccount = accountMapper.selectByUid(uid);
//                if (memAccount != null) {
//                    user = new User(memAccount);
//                    cacheUser(user);
//                }
//                uidMap.put(uid, user);
//            }
//            return user;
//        }
//    }
//
//    public User getByPhone(Long phone) {
//        if (phone < 0) {
//            return null;
//        }
//        User user = phoneMap.get(phone);
//        if (user == null) {
//            String key = keyPhone(phone);
//            String strUid = redisUtil.get(key);
//            if (strUid == null) {
//                MemAccount memAccount = accountMapper.selectByPhone(phone);
//                if (memAccount != null) {
//                    user = new User(memAccount);
//                    cacheUser(user);
//                }
//                phoneMap.put(phone, user);
//            } else {
//                return get(Long.valueOf(strUid));
//            }
//        }
//        return user;
//    }
//
//    public User getByEmail(String email) {
//        if (email == null || email.isEmpty()) {
//            return null;
//        }
//        User user = emailMap.get(email);
//        if (user != null) {
//            return user;
//        } else {
//            String key = keyEmail(email);
//            String strUid = redisUtil.get(key);
//            if (strUid != null) {
//                return get(Long.valueOf(strUid));
//            } else {
//                MemAccount memAccount = accountMapper.selectByEmail(email);
//                if (memAccount != null) {
//                    user = new User(memAccount);
//                    cacheUser(user);
//                }
//                emailMap.put(email, user);
//            }
//        }
//        return user;
//    }
//
//    public void put(User user) {
//        cacheUser(user);
//    }
//
//    public void destroy() {
//
//    }
//
//    public void cacheUser(User user) {
//        MemAccount memAccount = user.getMemAccount();
//        if (memAccount != null) {
//            String key = keyUser(memAccount.getUid());
//            redisUtil.objSet(key, memAccount);
//            uidMap.put(memAccount.getUid(), user);
//            String uid = String.valueOf(memAccount.getUid());
//            if (!"".equals(memAccount.getEmail())) {
//                redisUtil.set(keyEmail(memAccount.getEmail()), uid);
//                emailMap.put(memAccount.getEmail(), user);
//            }
//            if (memAccount.getPhone() > 0) {
//                redisUtil.set(keyPhone(memAccount.getPhone()), uid);
//                phoneMap.put(memAccount.getPhone(), user);
//            }
//        }
//    }
//}
