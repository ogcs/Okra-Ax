package org.okraAx.room.bean;

import java.io.Serializable;

/**
 * 玩家基本信息
 *
 * @author TinyZ.
 * @version 2017.06.09
 */
public class PlayerInfo implements Serializable {

    private static final long serialVersionUID = -5274794222386126070L;
    private long uid;   //  唯一ID
    private String name;    //  角色名
    private int figure;    //  形象
//    private   //  个人战绩 [胜率, 掉线率等等信息]

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
}
