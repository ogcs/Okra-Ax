package org.okraAx.room.module.chess;

import org.ogcs.utilities.TxtReader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/**
 *
 * 中国象棋工具
 * 用于检查测试象棋判断逻辑和规则是否正确
 * <a href="http://www.siyuetian.net/article/ShowArticle.asp?ArticleID=7275">象棋棋谱网站</a>
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/16
 */
public final class ChineseChessUtil {

    static final Map<Character, Integer> word2position = new HashMap<>();

    static {
        // 红坐标
        word2position.put('一', 8);
        word2position.put('二', 7);
        word2position.put('三', 6);
        word2position.put('四', 5);
        word2position.put('五', 4);
        word2position.put('六', 3);
        word2position.put('七', 2);
        word2position.put('八', 1);
        word2position.put('九', 0);
        // 黑方坐标
        word2position.put('１', 0);
        word2position.put('２', 1);
        word2position.put('３', 2);
        word2position.put('４', 3);
        word2position.put('５', 4);
        word2position.put('６', 5);
        word2position.put('７', 6);
        word2position.put('８', 7);
        word2position.put('９', 8);
    }

    private ChineseChessUtil() {
        // no-op
    }

    /**
     * Verify is the chess movable.
     * @param chessboard The chinese chess board
     * @param piece The pre-move chess
     * @param toX The position of X on chess board
     * @param toY The position of Y on chess board
     * @return Return true if the chess can move to target position.
     */
    public static boolean verify(Piece[][] chessboard, Piece piece, int toX, int toY) {
        if (toX < 0 || toX >= ChessConst.BOARD_WIDTH || toY < 0 || toY >= ChessConst.BOARD_HEIGHT) {
            return false;
        }
        switch (piece.getType()) {
            case ChessConst.PIECE_BING: {
                return !((piece.getSide() == ChessConst.SIZE_RED ? toY < piece.getY() : toY > piece.getY())
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
                if (piece.getSide() == ChessConst.SIZE_RED ? piece.getY() >= ChessConst.BOARD_HEIGHT / 2 : piece.getY() < ChessConst.BOARD_HEIGHT / 2) {
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
                int shifting = ChessConst.SIZE_RED == piece.getSide() ? 0 : 7;
                return !(toX < 3 || toX > 5 || toY < shifting || toY > 2 + shifting);
            }
            case ChessConst.PIECE_JIANG: {
                if (toX < 3 || toX > 5) {
                    return false;
                }
                if (piece.getX() == toX &&
                        piece.getSide() == ChessConst.SIZE_RED ? (toY >= 7 && toY < 10) : (toY >= 0 && toY < 3)) {
                    int minY = piece.getSide() == ChessConst.SIZE_RED ? 0 : 7;
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
                int shifting = ChessConst.SIZE_RED == piece.getSide() ? 0 : 7;
                return !(toY < shifting || toY > 2 + shifting);
            }
            default:
                return false;
        }
        return false;
    }

    public static int distance(int fromX, int fromY, int toX, int toY) {
        return Math.abs(fromX - toX) + Math.abs(fromY - toY);
    }

    public static boolean isClear(Piece[][] cells, boolean isRowFixed, int value, int from, int to) {
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

    // Chinese chess Map

    public static void verifyMap(ChineseChess chessboard, String filePath) throws IOException {
        byte[] bytes = TxtReader.readFileBytes(filePath);
        Piece[][] cells = chessboard.get();

        String map = new String(bytes);

        int side = 0;
        String[] split = map.split("\r\n");
        for (String round : split) {
            String[] steps = round.split("    ");
            for (String step : steps) {
                chessboard.setPz(step);
//                if (step.equals("将５进１")) {
//                    System.out.println();
//                }
                Piece from = lookup(step, cells, side);
                if (from == null) {
                    throw new IllegalStateException("找不到棋子");
                }
                try {
                    int toX = word2position.get(step.charAt(3));
                    char key = step.charAt(2);
                    switch (key) {
                        case '平': {
                            chessboard.onMove(from.getX(), from.getY(), toX, from.getY());
                            break;
                        }
                        case '进':
                        case '退':
                            switch (from.getType()) {
                                case ChessConst.PIECE_JU:
                                case ChessConst.PIECE_PAO:
                                    int range = range(step.charAt(3));
                                    chessboard.onMove(from.getX(), from.getY(), from.getX(), from.getY() + (key == '进' ? (side == 0 ? range : -range) : (side == 0 ? -range : range)));
                                    break;
                                case ChessConst.PIECE_MA: {
                                    int mX = toX - from.getX();
                                    int mY = Math.abs(mX) == 1 ? 2 : 1;
                                    chessboard.onMove(from.getX(), from.getY(), toX, from.getY() + (key == '进' ? (side == 0 ? mY : -mY) : (side == 0 ? -mY : mY)));
                                    break;
                                }
                                case ChessConst.PIECE_XIANG: {
                                    int mX = toX - from.getX();
                                    chessboard.onMove(from.getX(), from.getY(), toX, from.getY() + (key == '进' ? (side == 0 ? 2 : -2) : (side == 0 ? -2 : 2)));
                                    break;
                                }
                                case ChessConst.PIECE_SHI: {
                                    int mX = toX - from.getX();
                                    chessboard.onMove(from.getX(), from.getY(), toX, from.getY() + (key == '进' ? (side == 0 ? 1 : -1) : (side == 0 ? -1 : 1)));
                                    break;
                                }
                                case ChessConst.PIECE_BING:
                                case ChessConst.PIECE_JIANG:
                                    // 小兵 - 没有'退'
                                    chessboard.onMove(from.getX(), from.getY(), from.getX(), from.getY() + (key == '进' ? (side == 0 ? 1 : -1) : (side == 0 ? -1 : 1)));
                                    break;
                                default:
                                    throw new IllegalStateException("Unknown chess type");
                            }
                            break;
                    }
                    side = side == 0 ? 1 : 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int range(char ch) {
        switch (ch) {
            case '一':
            case '１':
                return 1;
            case '二':
            case '２':
                return 2;
            case '三':
            case '３':
                return 3;
            case '四':
            case '４':
                return 4;
            case '五':
            case '５':
                return 5;
            case '六':
            case '６':
                return 6;
            case '七':
            case '７':
                return 7;
            case '八':
            case '８':
                return 8;
            case '九':
            case '９':
                return 9;
            default:
                return -1;
        }
    }

    public static Piece lookup(String step, final Piece[][] cells, final int side) {
        int type = getChessType(step, 0);
        char char0 = step.charAt(0);
        if (char0 == '前' || char0 == '后') {
            List<Piece> integers = lookupPiece(cells, side, type);
            if (integers.size() >= 2) {
                if ((side == 0 && char0 == '前') || (side == 1 && char0 == '后')) {
                    return integers.stream().max((o1, o2) -> {
                        return o1.getY() - o2.getY();
                    }).get();
                } else {
                    Stream<Piece> stream = integers.stream();
                    Optional<Piece> min = stream.min((o1, o2) -> {
                        return o1.getY() - o2.getY();
                    });
                    return min.get();
                }
            }
        } else {
            int fromX = word2position.get(step.charAt(1));
            List<Piece> pieces = lookupPieceByX(cells, side, type, fromX);
            if (pieces.size() == 1) {
                return cells[fromX][pieces.get(0).getY()];
            } else {
                char key = step.charAt(2);
                for (Piece piece : pieces) {
                    int toY = piece.getY() + (key == '进' ? (side == 0 ? 1 : -1) : (side == 0 ? -1 : 1));
                    if (ChessConst.SIZE_RED == piece.getSide() ? (toY >= 0 && toY < 5) : (toY >= 5 && toY < 10)) {
                        return cells[fromX][piece.getY()];
                    }
                }
            }
        }
        return null;
    }

    public static List<Piece> lookupPiece(Piece[][] cells, int side, int type) {
        List<Piece> pieces = new ArrayList<>();
        for (Piece[] cell : cells) {
            if (cell != null) {
                for (int i = 0; i < cell.length; i++) {
                    Piece piece = cell[i];
                    if (piece != null && piece.getType() == type && piece.getSide() == side) {
                        pieces.add(piece);
                    }
                }
            }
        }
        return pieces;
    }

    public static List<Piece> lookupPieceByX(Piece[][] cells, int side, int type, int x) {
        List<Piece> pieces = new ArrayList<>();
        Piece[] cell = cells[x];
        if (cell != null) {
            for (Piece piece : cell) {
                if (piece != null && piece.getType() == type && piece.getSide() == side) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public static int getChessType(String str, int index) {
        switch (str.charAt(index)) {
            case '车':
                return ChessConst.PIECE_JU;
            case '马':
                return ChessConst.PIECE_MA;
            case '炮':
                return ChessConst.PIECE_PAO;
            case '象':
            case '相':
                return ChessConst.PIECE_XIANG;
            case '仕':
            case '士':
                return ChessConst.PIECE_SHI;
            case '将':
            case '帅':
                return ChessConst.PIECE_JIANG;
            case '兵':
            case '卒':
                return ChessConst.PIECE_BING;
            case '前':
            case '后':
                return getChessType(str, index + 1);
            default:
                throw new IllegalStateException("Unknown chess type");
        }
    }
}
