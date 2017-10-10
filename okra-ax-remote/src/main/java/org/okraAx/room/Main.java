package org.okraAx.room;

import org.okraAx.room.server.ServerManager;

/**
 * @author TinyZ.
 * @version 2017.03.27
 */
public class Main {

    public static void main(String[] args) {
        ServerManager.INSTANCE.start();
    }
}
