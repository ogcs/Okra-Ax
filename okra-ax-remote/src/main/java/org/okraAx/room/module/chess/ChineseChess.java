package org.okraAx.room.module.chess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.room.bean.RoomInfo;
import org.okraAx.room.fy.RemoteUser;
import org.okraAx.room.module.AbstractRoom;
import org.okraAx.room.module.Consts;
import org.okraAx.room.module.Room;
import org.okraAx.room.module.RoomFactory;

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
 * @version 2017.03.05
 */
public final class ChineseChess extends AbstractRoom {

    private static final Logger LOG = LogManager.getLogger(ChineseChess.class);

    public static final RoomFactory CHINESE_CHESS = new RoomFactory() {
        @Override
        public Room newInstance(RoomInfo<?> roomInfo) {
            //  TODO: 初始化
            return new ChineseChess(1);
        }
    };
    /**
     * 棋盘
     */
    private volatile Piece[][] chessboard;
    /**
     * 红棋
     */
    private volatile RemoteUser red;
    /**
     * 黑棋
     */
    private volatile RemoteUser black;
    /**
     * 当前游戏回合(玩家操作结束即为一个游戏回合结束)
     */
    private volatile AtomicInteger curRound = new AtomicInteger();
    /**
     * 胜利者
     */
    public volatile RemoteUser lastWinner;

    public ChineseChess(long roomId) {
        super(roomId);
        this.curRound.set(0);
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public void init() {

    }

    @Override
    public int maxPlayer() {
        return 2;
    }

    @Override
    public boolean onEnterWithPosition(RemoteUser user, int position) {
        if (user.getRoom() != null) {
            user.callback().callbackOnEnter(-1, null);
            return false;
        }
        synchronized (this) {
            if (position == 1 ? red != null : black != null) {
                user.callback().callbackOnEnter(-2, null);
                return false;
            }
            if (position == 1) {
                red = user;
            } else {
                black = user;
            }
            players.put(user.id(), user);
            user.setRoom(this);
        }
        for (RemoteUser p : players.values()) {
            p.callback().callbackOnEnter(0, user.userInfo());   //  TODO: 进入房间
        }
        return true;
    }

    @Override
    public void onReady(RemoteUser user, boolean ready) {
        if (user.getRoom() == null) {
            user.callback().callbackOnReady(-1, 0);
            return;
        }
        if (!players.containsKey(user.id())) {  //  不在此房间内
            user.callback().callbackOnReady(-2, 0);
            return;
        }
        user.userInfo().isReady = ready;
        for (RemoteUser user1 : players.values()) {
            user1.callback().callbackOnReady(0, user.id());
        }
        if (isFully()
                && this.status == Consts.ROOM_STATUS_WAIT
                && red.userInfo().isReady
                && black.userInfo().isReady) {
            onGameStart();
        }
    }

    public void onGameStart() {
        chessboard = new Piece[ChessConst.BOARD_WIDTH][ChessConst.BOARD_HEIGHT];
        for (Integer[] ary : ChessConst.LAYOUT) {
            Integer y1 = ary[0];
            Integer x1 = ary[1];
            Integer type = ary[2];
            chessboard[x1][y1] = new Piece(ChessConst.SIDE_RED, x1, y1, type);
            int y2 = ChessConst.BOARD_HEIGHT - y1 - 1;
            chessboard[x1][y2] = new Piece(ChessConst.SIDE_BLACK, x1, y2, type);
        }
        //  初始化完成
        for (RemoteUser user : players.values()) {
            user.callback().callbackOnGameStart();
        }
        this.status = Consts.ROOM_STATUS_GAMING;
    }

    @Override
    public void onExit(Long uid) {
        RemoteUser remoteUser = players.remove(uid);
        if (remoteUser != null) {
            remoteUser.setRoom(null);

            this.status = Consts.ROOM_STATUS_END;
            this.lastWinner = red.id() == uid ? black : red;
            onGameEnd();
            //  离开房间
            for (RemoteUser p : players.values()) {
                p.callback().callbackOnExit(uid);
            }
        }
    }

    /**
     * 移动棋子
     */
    public boolean onMoveChess(RemoteUser userInfo, int fromX, int fromY, int toX, int toY) {
        int round = curRound.get();
        if (round % 2 == 0 ? userInfo != red : userInfo != black) {
            //  非玩家操作回合
            userInfo.callback().callbackOnMoveChess(-1, fromX, fromY, toX, toY);
            return false;
        }
        //  移动棋子
        Piece fromCell = chessboard[fromX][fromY];
        if (fromCell != null
                && isChessMovable(chessboard, fromCell, toX, toY)
                && fromCell.getSide() == (round % 2)) {
            LOG.debug("[" + (round / 2 + 1) + "]: (" + fromX + ", " + fromY + ") => (" + toX + ", " + toY + ")");

            Piece toCell = chessboard[toX][toY];
            fromCell.setX(toX);
            fromCell.setY(toY);
            chessboard[toX][toY] = fromCell;
            chessboard[fromX][fromY] = null;
            // Push
            for (RemoteUser user : players.values()) {
                user.callback().callbackOnMoveChess(0, fromX, fromY, toX, toY);
            }
            if (toCell != null && toCell.getType() == ChessConst.PIECE_JIANG) {
                this.status = Consts.ROOM_STATUS_END;
                this.lastWinner = toCell.getSide() == ChessConst.SIDE_RED ? black : red;
                onGameEnd();
            }
            curRound.getAndIncrement();
            return true;
        } else {
            LOG.info("Unknown Error : [" + (round / 2 + 1) + "]: (" + fromX + ", " + fromY + ") => (" + toX + ", " + toY + ")");
            return false;
        }
    }

    public void onGameEnd() {
        //  TODO: 游戏结束奖励结算， 战绩统计等等

        for (RemoteUser user : players.values()) {
            user.callback().callbackOnGameEnd(lastWinner == red ? ChessConst.SIDE_RED : ChessConst.SIDE_BLACK);
        }
    }

    public boolean isGameEnd() {
        return status == Consts.ROOM_STATUS_END;
    }

    /**
     * Verify is the chess movable.
     *
     * @param chessboard The chinese chess board
     * @param piece      The pre-move chess
     * @param toX        The position of X on chess board
     * @param toY        The position of Y on chess board
     * @return Return true if the chess can move to target position.
     */
    private boolean isChessMovable(Piece[][] chessboard, Piece piece, int toX, int toY) {
        if (toX < 0 || toX >= ChessConst.BOARD_WIDTH || toY < 0 || toY >= ChessConst.BOARD_HEIGHT) {
            return false;
        }
        switch (piece.getType()) {
            case ChessConst.PIECE_BING: {
                return !((piece.getSide() == ChessConst.SIDE_RED ? toY < piece.getY() : toY > piece.getY())
                        && (distance(piece.getX(), piece.getY(), toX, toY) != 1));
            }
            case ChessConst.PIECE_PAO: {
                if (piece.getX() == toX) {
                    if (chessboard[toX][toY] == null) {
                        return isClear(chessboard, true, toX, Math.min(piece.getY(), toY), Math.max(piece.getY(), toY));
                    } else {
                        return !isClear(chessboard, true, toX, Math.min(piece.getY(), toY), Math.max(piece.getY(), toY));
                    }
                } else if (piece.getY() == toY) {
                    if (chessboard[toX][toY] == null) {
                        return isClear(chessboard, false, toY, Math.min(piece.getX(), toX), Math.max(piece.getX(), toX));
                    } else {
                        return !isClear(chessboard, false, toY, Math.min(piece.getX(), toX), Math.max(piece.getX(), toX));
                    }
                } else {
                    return false;
                }
            }
            case ChessConst.PIECE_MA: {
                if (distance(piece.getX(), piece.getY(), toX, toY) != 3) {
                    return false;
                }
                if (piece.getX() - toX == 2) {
                    return chessboard[piece.getX() - 1][piece.getY()] == null;
                } else if (piece.getX() - toX == -2) {
                    return chessboard[piece.getX() + 1][piece.getY()] == null;
                } else if (piece.getY() - toY == 2) {
                    return chessboard[piece.getX()][piece.getY() - 1] == null;
                } else if (piece.getY() - toY == -2) {
                    return chessboard[piece.getX()][piece.getY() + 1] == null;
                }
                break;
            }
            case ChessConst.PIECE_JU: {
                if (piece.getX() == toX) {
                    return isClear(chessboard, true, toX, Math.min(piece.getY(), toY), Math.max(piece.getY(), toY));
                } else
                    return piece.getY() == toY && isClear(chessboard, false, toY, Math.min(piece.getX(), toX), Math.max(piece.getX(), toX));
            }
            case ChessConst.PIECE_XIANG: {
                if (distance(piece.getX(), piece.getY(), toX, toY) != 4) {
                    return false;
                }
                if (piece.getSide() == ChessConst.SIDE_RED ? piece.getY() >= ChessConst.BOARD_HEIGHT / 2 : piece.getY() < ChessConst.BOARD_HEIGHT / 2) {
                    return false;
                }
                if (piece.getX() > toX) {
                    if (piece.getY() > toY) {
                        return chessboard[piece.getX() - 1][piece.getY() - 1] == null;
                    } else {
                        return chessboard[piece.getX() - 1][piece.getY() + 1] == null;
                    }
                } else {
                    if (piece.getY() > toY) {
                        return chessboard[piece.getX() + 1][piece.getY() - 1] == null;
                    } else {
                        return chessboard[piece.getX() + 1][piece.getY() + 1] == null;
                    }
                }
            }
            case ChessConst.PIECE_SHI: {
                if (distance(piece.getX(), piece.getY(), toX, toY) != 2) {
                    return false;
                }
                int shifting = ChessConst.SIDE_RED == piece.getSide() ? 0 : 7;
                return !(toX < 3 || toX > 5 || toY < shifting || toY > 2 + shifting);
            }
            case ChessConst.PIECE_JIANG: {
                if (toX < 3 || toX > 5) {
                    return false;
                }
                if (piece.getX() == toX &&
                        piece.getSide() == ChessConst.SIDE_RED ? (toY >= 7 && toY < 10) : (toY >= 0 && toY < 3)) {
                    int minY = piece.getSide() == ChessConst.SIDE_RED ? 0 : 7;
                    for (int i = minY; i <= minY + 2; i++) {
                        if (chessboard[piece.getX()][i] != null) {
                            Piece piece1 = chessboard[piece.getX()][i];
                            if (piece1.getType() == ChessConst.PIECE_JIANG) {
                                if (isClear(chessboard, true, piece.getX(), Math.min(piece.getY(), piece1.getY()), Math.max(piece.getY(), piece1.getY()))) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (distance(piece.getX(), piece.getY(), toX, toY) != 1) {
                    return false;
                }
                int shifting = ChessConst.SIDE_RED == piece.getSide() ? 0 : 7;
                return !(toY < shifting || toY > 2 + shifting);
            }
            default:
                return false;
        }
        return false;
    }

    private int distance(int fromX, int fromY, int toX, int toY) {
        return Math.abs(fromX - toX) + Math.abs(fromY - toY);
    }

    private boolean isClear(Piece[][] cells, boolean isRowFixed, int value, int from, int to) {
        boolean isClear = true;
        for (int i = from + 1; i < to; i++) {
            if (isRowFixed) {
                isClear &= cells[value][i] == null;
            } else {
                isClear &= cells[i][value] == null;
            }
        }
        return isClear;
    }
}
