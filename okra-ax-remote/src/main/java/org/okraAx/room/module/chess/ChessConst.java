package org.okraAx.room.module.chess;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public interface ChessConst {

    //
    int EVENT_GET_READY = 1;        //  准备
    int EVENT_CANCEL_READY = 2;     //  取消准备
    int EVENT_INIT_COMPLETED = 3;   //  初始化完成 - 游戏开始
    int EVENT_GAME_END = 4;         //  游戏结束
    int EVENT_CHESS_MOVE = 5;       //  移动棋子
    int EVENT_EXIT = 6;             //  退出


    // 红黑双方
    int SIZE_RED = 0;
    int SIZE_BLACK = 1;
    //  棋盘宽高
    int BOARD_WIDTH = 9;
    int BOARD_HEIGHT = 10;
    //  棋子枚举
    int PIECE_JIANG = 7;
    int PIECE_SHI = 6;
    int PIECE_XIANG = 5;
    int PIECE_JU = 4;
    int PIECE_MA = 3;
    int PIECE_PAO = 2;
    int PIECE_BING = 1;
    //  棋盘初始布局
    Integer[][] LAYOUT = new Integer[][]{
            {0, 0, PIECE_JU},
            {0, 1, PIECE_MA},
            {0, 2, PIECE_XIANG},
            {0, 3, PIECE_SHI},
            {0, 4, PIECE_JIANG},
            {0, 5, PIECE_SHI},
            {0, 6, PIECE_XIANG},
            {0, 7, PIECE_MA},
            {0, 8, PIECE_JU},

            {2, 1, PIECE_PAO},
            {2, 7, PIECE_PAO},

            {3, 0, PIECE_BING},
            {3, 2, PIECE_BING},
            {3, 4, PIECE_BING},
            {3, 6, PIECE_BING},
            {3, 8, PIECE_BING},
    };
}
