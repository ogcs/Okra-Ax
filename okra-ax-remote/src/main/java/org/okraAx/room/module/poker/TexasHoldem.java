package org.okraAx.room.module.poker;

import org.okraAx.room.module.AbstractRoom;

/**
 * 德州扑克
 * @author TinyZ.
 * @version 2017.09.24
 */
public class TexasHoldem extends AbstractRoom {

    public TexasHoldem(long roomId) {
        super(roomId);
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public void init() {

    }

    @Override
    public int maxPlayer() {
        return 0;
    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGameEnd() {

    }
}
