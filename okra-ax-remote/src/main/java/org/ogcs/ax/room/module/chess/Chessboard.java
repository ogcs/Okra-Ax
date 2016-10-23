package org.ogcs.ax.room.module.chess;

import com.google.protobuf.Any;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.room.module.Room;
import org.ogcs.ax.room.module.RoomManager;
import org.ogcs.ax.gpb3.GpbD.Push;
import org.ogcs.gpb.generated.GpbChess.PushChessInit;
import org.ogcs.gpb.generated.GpbChess.PushChessMove;
import org.ogcs.gpb.generated.GpbChess.PushReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.ax.utilities.AxReplys;
import org.ogcs.ax.component.manager.ConnectorManager;

import java.util.*;
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
 * @date : 2016/4/13
 */
public class Chessboard implements Room {

    private static final Logger LOG = LogManager.getLogger(Chessboard.class);
    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private RoomManager roomManager = (RoomManager) AppContext.getBean(SpringContext.MODULE_ROOM_MANAGER);

    private Piece[][] chessboard;
    private long id;
    private int round;
    private AtomicInteger hand = new AtomicInteger();
    // uid list
    private int count = 0;
    private Long[] uids = new Long[2];
    private Map<String, List<Long>> gate4UidsMap = new HashMap<>();

    public Chessboard(long id) {
        this.id = id;
        this.round = 0;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public boolean isFully() {
        return players().size() >= 2;
    }

    @Override
    public void enter(String gate, long uid) {
        if (count >= 2) {
            return;
        }
        uids[count] = uid;
        List<Long> longs = gate4UidsMap.get(gate);
        if (longs == null) {
            longs = new ArrayList<>();
            gate4UidsMap.put(gate, longs);
        }
        longs.add(uid);
        count++;
    }

    @Override
    public void init() {
        chessboard = new Piece[ChessConst.BOARD_WIDTH][ChessConst.BOARD_HEIGHT];
        for (Integer[] ary : ChessConst.LAYOUT) {
            Integer y1 = ary[0];
            Integer x1 = ary[1];
            Integer type = ary[2];
            chessboard[x1][y1] = new Piece(ChessConst.SIZE_RED, x1, y1, type);
            int y2 = ChessConst.BOARD_HEIGHT - y1 - 1;
            chessboard[x1][y2] = new Piece(ChessConst.SIZE_BLACK, x1, y2, type);
        }
        push(PushChessInit.INIT_FIELD_NUMBER, PushChessInit.init, PushChessInit.getDefaultInstance());
    }

    public Piece[][] get() {
        return chessboard;
    }

    @Override
    public Set<Long> players() {
        Set<Long> players = new HashSet<>();
        Collections.addAll(players, uids);
        return players;
//        return uidList.stream().map(session -> {
//            return session;
//        }).collect(Collectors.toSet());
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

    @Override
    public void exit(Long uid) {
        onGameEnd(index(uid) == ChessConst.SIZE_RED ? ChessConst.SIZE_BLACK : ChessConst.SIZE_RED);
    }

    @Override
    public void destroy() {

    }

    public boolean isOperable(Long uid) {
        return count == 2 && uids[(round % 2)].equals(uid);
    }

    public void onGameEnd(int side) {
        PushReport report = PushReport.newBuilder()
                .setSide(side)
                .build();
        // Push
        push(PushReport.REPORT_FIELD_NUMBER, PushReport.report, report);
        roomManager.destroy(id());
    }

    private String pz = "";

    public String getPz() {
        return pz;
    }

    public void setPz(String pz) {
        this.pz = pz;
    }

    public boolean move(int fromX, int fromY, int toX, int toY) {
        Piece fromCell = chessboard[fromX][fromY];
        if (fromCell != null
                && ChineseChessUtil.verify(chessboard, fromCell, toX, toY)
                && fromCell.getSide() == (round % 2)) {
            if (round % 2 == 0) {
                hand.getAndIncrement();
            }
            LOG.info("[" + hand.get() + "]:" + pz + ": (" + fromX + ", " + fromY + ") => (" + toX + ", " + toY + ")");
            round++;
            Piece toCell = chessboard[toX][toY];

            fromCell.setX(toX);
            fromCell.setY(toY);
            chessboard[toX][toY] = fromCell;
            chessboard[fromX][fromY] = null;
            // Push
            PushChessMove message = PushChessMove.newBuilder()
                    .setFromX(fromX)
                    .setFromY(fromY)
                    .setToX(toX)
                    .setToY(toY)
                    .build();
            push(PushChessMove.CHESSMOVE_FIELD_NUMBER, PushChessMove.chessMove, message);
            if (toCell != null && toCell.getType() == ChessConst.PIECE_JIANG) {
                onGameEnd(toCell.getSide() == ChessConst.SIZE_RED ? ChessConst.SIZE_BLACK : ChessConst.SIZE_RED);
            }
            return true;
        } else {
            LOG.info("Unknown Error : [" + hand.get() + "]:" + pz + ": (" + fromX + ", " + fromY + ") => (" + toX + ", " + toY + ")");
            return false;
        }
    }

    public void push(int id, GeneratedMessage.GeneratedExtension extension, Message message) {
        connectorManager.pushById(
                AxReplys.axOutbound(id,
                        Push.newBuilder()
                                .setId(id)
                                .setMsg(Any.pack(message))
                                .build(),
                        uids),
                gate4UidsMap.keySet().toArray());
    }





}
