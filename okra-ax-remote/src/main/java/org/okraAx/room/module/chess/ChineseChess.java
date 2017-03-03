package org.okraAx.room.module.chess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.gpb.generated.PushChessInit;
import org.ogcs.gpb.generated.PushReport;
import org.okraAx.room.module.AbstractTable;
import org.okraAx.room.module.Api;
import org.okraAx.v3.chess.beans.MsgChessMove;
import org.okraAx.v3.room.beans.MsgGetReady;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 标准中国象棋坐标系为红黑独立，从右至左，从1到9坐标
 * <p>
 * 程序中为了简化坐标系转换，红黑双方使用一个坐标系， <strong>坐标原点(0,0)为棋盘左下角(红方九位置)<strong/>
 * <br/>
 * <br/>
 * 棋盘布局如下:
 * <table>
 * <tr> <td>俥</td> <td>馬</td> <td>象</td> <td>士</td> <td>将</td> <td>士</td> <td>象</td> <td>馬</td> <td>车</td> </tr>
 * <tr></tr>
 * <tr> <td></td> <td>砲</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td>砲</td> <td></td> </tr>
 * <tr> <td>卒</td> <td></td> <td>卒</td> <td></td> <td>卒</td> <td></td> <td>卒</td> <td></td> <td>卒</td> </tr>
 * <tr></tr>
 * <tr></tr>
 * <tr> <td>卒</td> <td></td> <td>卒</td> <td></td> <td>卒</td> <td></td> <td>卒</td> <td></td> <td>卒</td> </tr>
 * <tr> <td></td> <td>炮</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td>炮</td> <td></td> </tr>
 * <tr></tr>
 * <tr> <td>俥</td> <td>傌</td> <td>相</td> <td>仕</td> <td>帅</td> <td>仕</td> <td>相</td> <td>傌</td> <td>俥</td> </tr>
 * </table>
 * <p>
 * <br/>
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 */
public final class ChineseChess extends AbstractTable {

    private static final Logger LOG = LogManager.getLogger(ChineseChess.class);
//    private RoomManager roomManager = (RoomManager) AppContext.getBean(SpringContext.MODULE_ROOM_MANAGER);

    /**
     * 房间唯一ID
     */
    private long roomId;
    private Piece[][] chessboard;
    /**
     * 当前回合
     */
    private int round;
    private AtomicInteger hand = new AtomicInteger();
    // uid list
    private int count = 0;
    private Long[] uids = new Long[2];
    private Map<String, List<Long>> gate4UidsMap = new HashMap<>();

    private Map<Long, Boolean> readys = new ConcurrentHashMap<>();


    public ChineseChess(long roomId) {
        this.roomId = roomId;
        this.round = 0;
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public void init() {
        //
        chessboard = new Piece[ChessConst.BOARD_WIDTH][ChessConst.BOARD_HEIGHT];
        for (Integer[] ary : ChessConst.LAYOUT) {
            Integer y1 = ary[0];
            Integer x1 = ary[1];
            Integer type = ary[2];
            chessboard[x1][y1] = new Piece(ChessConst.SIZE_RED, x1, y1, type);
            int y2 = ChessConst.BOARD_HEIGHT - y1 - 1;
            chessboard[x1][y2] = new Piece(ChessConst.SIZE_BLACK, x1, y2, type);
        }
        //  初始化完成
        dispatchEvent(ChessConst.EVENT_INIT_COMPLETED, PushChessInit.getDefaultInstance());
    }

    @Override
    public int maxPlayer() {
        return 2;
    }

    public Piece[][] get() {
        return chessboard;
    }

    public int index(long uid) {
        for (int i = 0; i < uids.length; i++) {
            Long aLong = uids[i];
            if (uid == aLong) {
                return i;
            }
        }
        return -1;
    }

    public void onAction(int type, Object data) {
        if (dispatcher == null) return;
        dispatcher.dispatchEvent(type, this, data);
    }

    @Override
    public void onExit(Long uid) {
        super.onExit(uid);

        onGameEnd(index(uid) == ChessConst.SIZE_RED ? ChessConst.SIZE_BLACK : ChessConst.SIZE_RED);
    }

    public boolean isOperable(Long uid) {
        return count == 2 && uids[(round % 2)].equals(uid);
    }

    public void onReady(long uid, boolean ready) {
        if (ready) {
            readys.put(uid, true);
        } else if (readys.containsKey(uid)) {
            readys.remove(uid);
        }
        broadcast(Api.CALLBACK_20005_GET_READY, MsgGetReady.newBuilder()
                .setUid(uid)
                .setReady(ready)
                .build());
        if (readys.size() >= maxPlayer())
            init();
    }

    private String pz = "";

    public String getPz() {
        return pz;
    }

    public void setPz(String pz) {
        this.pz = pz;
    }

    public void onGameEnd(int side) {
        broadcast(1, PushReport.newBuilder()
                .setSide(side)
                .build());
//        roomManager.destroy(id());
    }

    public boolean onMove(int fromX, int fromY, int toX, int toY) {
        Piece fromCell = chessboard[fromX][fromY];
        if (fromCell != null
                && ChineseChessUtil.verify(chessboard, fromCell, toX, toY)
                && fromCell.getSide() == (round % 2)) {
            if (round % 2 == 0) {
                hand.getAndIncrement();
            }
            LOG.debug("[" + hand.get() + "]:" + pz + ": (" + fromX + ", " + fromY + ") => (" + toX + ", " + toY + ")");
            round++;
            Piece toCell = chessboard[toX][toY];

            fromCell.setX(toX);
            fromCell.setY(toY);
            chessboard[toX][toY] = fromCell;
            chessboard[fromX][fromY] = null;
            // Push
            broadcast(20006, MsgChessMove.newBuilder()
                    .setFromX(fromX).setFromY(fromY)
                    .setToX(toX).setToY(toY)
                    .build());
            if (toCell != null && toCell.getType() == ChessConst.PIECE_JIANG) {
                onGameEnd(toCell.getSide() == ChessConst.SIZE_RED ? ChessConst.SIZE_BLACK : ChessConst.SIZE_RED);
            }
            return true;
        } else {
            LOG.info("Unknown Error : [" + hand.get() + "]:" + pz + ": (" + fromX + ", " + fromY + ") => (" + toX + ", " + toY + ")");
            return false;
        }
    }

}
