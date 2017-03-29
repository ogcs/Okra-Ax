package org.okraAx.room.component;

import org.okraAx.room.fy.Player;
import org.okraAx.room.module.Room;
import org.okraAx.room.module.chess.ChineseChess;
import org.okraAx.utilities.SessionHelper;
import org.springframework.stereotype.Component;

/**
 * @author TinyZ
 * @date 2017-02-12.
 */
@Component
public class ChessComponent {


    /**
     * 移动棋子
     */
    public void onMoveChess(int fromX, int fromY, int toX, int toY) {
        Player player = SessionHelper.curPlayer();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) return;
        if (room instanceof ChineseChess) {
            ChineseChess table = (ChineseChess) room;
            table.onMove(fromX, fromY, toX, toY);
        }
    }

}
