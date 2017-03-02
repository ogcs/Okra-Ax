package org.okraAx.logic.persistence.domain;

import java.io.Serializable;

/**
 * 账号信息
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public class MemAccount implements Serializable {

    private Long uid;                //  玩家唯一ID
    private String name;               //  昵称
    private Integer figure;             //  头像
    private String account;            //  登陆账号
    private String psw;                //  登陆密码
    private String email;              //  注册邮箱
    private Long phone;              //  电话
    private Integer channel;            //  渠道
    private Integer platform;           //  平台
    private Long timeCreate;         //  创建账号时间  时间戳(毫秒)
    private Long timeLastLogin;      //  最后一次登录时间 时间戳(毫秒)
    private String ipCreate;           //  创建IP
    private String ipLastLogin;        //  最后一次登录ip
    private Integer vipLevel = 0;       //  VIP等级
    private Integer vipExp = 0;         //  VIP经验值
    private Double rmb;            //  账户余额

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFigure() {
        return figure;
    }

    public void setFigure(Integer figure) {
        this.figure = figure;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Long getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Long getTimeLastLogin() {
        return timeLastLogin;
    }

    public void setTimeLastLogin(Long timeLastLogin) {
        this.timeLastLogin = timeLastLogin;
    }

    public String getIpCreate() {
        return ipCreate;
    }

    public void setIpCreate(String ipCreate) {
        this.ipCreate = ipCreate;
    }

    public String getIpLastLogin() {
        return ipLastLogin;
    }

    public void setIpLastLogin(String ipLastLogin) {
        this.ipLastLogin = ipLastLogin;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Integer getVipExp() {
        return vipExp;
    }

    public void setVipExp(Integer vipExp) {
        this.vipExp = vipExp;
    }

    public Double getRmb() {
        return rmb;
    }

    public void setRmb(Double rmb) {
        this.rmb = rmb;
    }
}
