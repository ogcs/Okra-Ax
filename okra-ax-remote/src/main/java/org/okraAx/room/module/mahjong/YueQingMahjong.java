package org.okraAx.room.module.mahjong;

import org.okraAx.room.fy.Player;
import org.okraAx.room.module.AbstractRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TinyZ.
 * @since 2.0
 * @version  2017.02.13.
 */
public class YueQingMahjong extends AbstractRoom {

    private static final Byte[] pool = new Byte[] {
            //  条
            0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
            //  筒
            0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19,
            //  万
            0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29,
            //  东南西北中发白
            0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37
    };
    private List<Byte> list = new ArrayList<>();

    @Override
    public int type() {
        return 0;
    }

    @Override
    public int maxPlayer() {
        return 4;
    }

    @Override
    public void onReady(Player player, boolean ready) {

    }

    @Override
    public void init() {

    }

    public void initMahjong() {
        List<Byte> list = new ArrayList<>();
        for (Byte card : pool) {
            for (int i = 0; i < 4; i++) {
                list.add(card);
            }
        }
        Map<Integer, Byte> map = new HashMap<>();
        while(map.size() < 136) {
            int ind = (int) (list.size() * Math.random());
            map.put(ind,list.get(ind) );
            list.remove(ind);
        }

    }


}
