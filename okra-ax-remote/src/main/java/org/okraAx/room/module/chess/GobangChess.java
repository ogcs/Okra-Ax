package org.okraAx.room.module.chess;

import org.okraAx.room.fy.RemoteUser;
import org.okraAx.room.module.AbstractRoom;

/**
 * 五子棋
 *
 * @version 2017.05.30
 */
public final class GobangChess extends AbstractRoom {

    private static final int WIDTH = 19;
    private static final int HEIGHT = 19;

    private Piece[][] chessboard;

    public GobangChess(long roomId) {
        super(roomId);
    }

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
    public void onReady(RemoteUser remoteUser, boolean ready) {

    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGameEnd() {

    }

    /**
     * 放下棋子
     *
     * @param x x坐标
     * @param y y坐标
     */
    public void onPutDownChess(RemoteUser user, int x, int y) {
        if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {//  参数非法
            user.callback().callbackOnPutDownChess(-1, 0, 0,0);
            return;
        }
        if (chessboard[x][y] != null) {
            user.callback().callbackOnPutDownChess(-2, 0, 0,0);//  不能落子
            return;
        }
        chessboard[x][y] = new Piece(1, x, y, 10);


        //  检查胜负
    }

    public void isWin(int x, int y) {

    }
}
