package com.lj.kernel.module.chess;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/13
 */
public class ChessMain {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext cpac = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");
        cpac.registerShutdownHook();


        Chessboard chessboard = new Chessboard(10);
        chessboard.init();

        ChineseChessUtil.verifyMap(chessboard, "conf/map5.data");

        System.out.println("结束");
    }
}
