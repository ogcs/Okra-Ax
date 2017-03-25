package org.okraAx.room.fy.impl;

import com.google.protobuf.Descriptors;
import org.ogcs.app.AppContext;
import org.ogcs.app.Procedure;
import org.okraAx.common.modules.FyChessService;
import org.okraAx.internal.v3.AbstractGpbService;
import org.okraAx.room.Player;
import org.okraAx.room.component.ChessComponent;
import org.okraAx.room.component.RoomComponent;
import org.okraAx.room.module.Room;
import org.okraAx.room.module.chess.ChineseChess;
import org.okraAx.utilities.SessionHelper;
import org.okraAx.v3.chess.services.FyChessSi;

/**
 * @author TinyZ
 * @date 2017-02-12.
 */
//@Component
public final class FyChessImpl extends AbstractGpbService implements FyChessService {

    private static final Descriptors.ServiceDescriptor DESC =
            FyChessSi.getDescriptor().findServiceByName("ChessService");

    private RoomComponent roomComponent = AppContext.getBean(RoomComponent.class);
    private ChessComponent chessComponent = AppContext.getBean(ChessComponent.class);

    /**
     *
     */
    @Procedure
    @Override
    public void onShowRoomStatus() {

    }

    @Procedure
    @Override
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

    @Override
    public Descriptors.ServiceDescriptor desc() {
        return DESC;
    }
}
