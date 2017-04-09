package org.okraAx.test;

import org.junit.Test;
import org.okraAx.room.fy.LogicClient;

/**
 * @author TinyZ.
 * @version 2017.04.04
 */
public class LogicClientTest {

    @Test
    public void test() {

        LogicClient client = new LogicClient("127.0.0.1", 9005);
        client.logicClient().callbackEnterChannel(1);






    }

}
