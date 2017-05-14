package org.okraAx.login.bean;

import java.io.Serializable;

/**
 * @author TinyZ.
 * @version 2017.05.12
 */
public class AccountBean implements Serializable {

    private static final long serialVersionUID = 6660603190431154942L;
    private long uid;                //  游戏的玩家唯一ID
    private String openId;           //  游戏登录帐号(接入的平台帐号或ID)
    private long timeCreateAccount;  //  创建账号时间(单位:毫秒)
    private String ip;               //  创建IP
    private long lastLoginTime;      //  最后一次登录时间(单位:毫秒)
    private long lastLogoutTime;     //  最后一次登出时间(单位:毫秒)
    private String lastLoginIP;      //  最后一次登录ip
    private volatile int status;     //  状态[1.普通, 2:封禁登录，3:封禁聊天， 9:封禁全部]
    private volatile int identify;   //  标识[1:普通, 2:未成年, 3:游客， 9:福利账户]
    private volatile int gold;       //  金币(充值)
    private volatile int totalGold;  //  累计充值

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getTimeCreateAccount() {
        return timeCreateAccount;
    }

    public void setTimeCreateAccount(long timeCreateAccount) {
        this.timeCreateAccount = timeCreateAccount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public long getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(long lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdentify() {
        return identify;
    }

    public void setIdentify(int identify) {
        this.identify = identify;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(int totalGold) {
        this.totalGold = totalGold;
    }
}
