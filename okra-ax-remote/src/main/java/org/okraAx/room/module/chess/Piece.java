package org.okraAx.room.module.chess;

/**
 * 象棋棋子
 *
 */
public class Piece {

    private int side;   //  棋子拥有方
    private int x;      //  棋子的x坐标
    private int y;      //  棋子的y坐标
    private int type;   //  棋子的类型

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
