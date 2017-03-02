package org.okraAx.room.fy.impl;

import org.ogcs.app.AppContext;
import org.ogcs.app.Procedure;
import org.okraAx.room.Player;
import org.okraAx.room.component.ChessComponent;
import org.okraAx.room.component.RoomComponent;
import org.okraAx.internal.v3.interfaces.FyChessService;
import org.okraAx.room.module.Room;
import org.okraAx.room.module.chess.ChineseChess;
import org.okraAx.internal.v3.FySession;
import org.okraAx.internal.v3.AbstractGpbService;
import org.okraAx.v3.chess.services.FyChessSi;

/**
 * @author TinyZ
 * @date 2017-02-12.
 */
//@Component
public final class FyChessServiceImpl extends AbstractGpbService implements FyChessService<FySession> {

    private RoomComponent roomComponent = AppContext.getBean(RoomComponent.class);
    private ChessComponent chessComponent = AppContext.getBean(ChessComponent.class);

    public FyChessServiceImpl() {
        super(FyChessSi.getDescriptor().findServiceByName("ChessService"));
    }

    /**
     *
     */
    @Procedure
    public void onShowRoomStatus(FySession session) {

    }

    @Procedure
    public void onMoveChess(FySession session, int fromX, int fromY, int toX, int toY) {
        Player player = (Player) session.getConnector();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) return;
        if (room instanceof ChineseChess) {
            ChineseChess table = (ChineseChess) room;
            table.onMove(fromX, fromY, toX, toY);
        }
    }
}
