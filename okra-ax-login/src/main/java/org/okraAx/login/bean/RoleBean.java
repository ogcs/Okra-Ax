package org.okraAx.login.bean;

import java.io.Serializable;

/**
 * @author TinyZ.
 * @version 2017.05.12
 */
public class RoleBean implements Serializable {

    private static final long serialVersionUID = 316314981406576254L;
    private long uid;                   // 账户唯一ID
    private volatile String name;       // 角色名
    private volatile int figure;        // 形象
    private volatile long silver;       // 银币

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public long getSilver() {
        return silver;
    }

    public void setSilver(long silver) {
        this.silver = silver;
    }
}
