package org.okraAx.room.module.gameRule;

import org.okraAx.room.bean.RoomInfo;
import org.okraAx.room.module.GameRule;
import org.okraAx.room.module.chess.Piece;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TinyZ.
 * @version 2017.09.21
 */
public class ChineseChessRule implements GameRule {

    private Piece[][] chessboard;
    /**
     * 当前回合
     */
    private int curRound;
    private AtomicInteger hand = new AtomicInteger();

    @Override
    public void initGameRoom(RoomInfo bean) {

    }

    @Override
    public int status() {
        return 0;
    }

    @Override
    public boolean isFully() {
        return false;
    }

    @Override
    public boolean isGameEnd() {
        return false;
    }
}
