package org.okraAx.room.component;

import org.okraAx.room.fy.RemoteUser;
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
        RemoteUser remoteUser = SessionHelper.curPlayer();
        if (remoteUser == null) return;
        Room room = remoteUser.getRoom();
        if (room == null) return;
        if (room instanceof ChineseChess) {
            ChineseChess table = (ChineseChess) room;
            table.onMove(fromX, fromY, toX, toY);
        }
    }

    public void onPutDownw(int x, int y) {

    }

}
