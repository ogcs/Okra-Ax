package org.okraAx.room.module.chess;

import org.okraAx.room.fy.Player;
import org.okraAx.room.module.AbstractTable;

/**
 * 五子棋
 *
 * @version 2017.05.30
 */
public final class FiveChess extends AbstractTable {

    private static final int WIDTH = 19;
    private static final int HEIGHT = 19;

    private Piece[][] chessboard;

    @Override
    public int type() {
        return 0;
    }

    @Override
    public void init() {
        chessboard = new Piece[WIDTH][HEIGHT];

    }

    @Override
    public int maxPlayer() {
        return 2;
    }

    @Override
    public void onReady(Player player, boolean ready) {

    }

    /**
     * 放下棋子
     *
     * @param x x坐标
     * @param y y坐标
     */
    public void putDown(long uid, int x, int y) {
        if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
            push(uid, null);    //  参数非法
            return;
        }
        if (chessboard[x][y] != null) {
            push(uid, null);    //  不能落子
            return;
        }
        chessboard[x][y] = new Piece(1, x, y, 10);


        //  检查胜负

    }

    public void isWin(int x, int y) {

    }
}
