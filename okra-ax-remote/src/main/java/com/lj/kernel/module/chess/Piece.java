package com.lj.kernel.module.chess;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/13
 */
public class Piece {

    private int side;
    private int x;
    private int y;
    private int type;

    public Piece(int side, int x, int y, int type) {
        this.side = side;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
