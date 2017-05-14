package client;

import org.junit.Test;
import org.okraAx.rebot.FakeClient;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.GpcVoid;
import org.okraAx.v3.login.beans.CreateRoleBean;

/**
 * @author TinyZ.
 * @version 2017.05.10
 */
public class FakeClientTest {

    @Test
    public void testLogin() {

        FakeClient client = new FakeClient();
        client.addLastAction(()->{
            client.writeAndFlush(GpcCall.newBuilder()
                    .setMethod("onCreateRole")
                    .setParams(CreateRoleBean.newBuilder()
                            .setOpenId("xx-openId")
                            .setName("xx-name")
                            .setFigure(1)
                            .build().toByteString())
                    .build());
            client.waitForCallback("callbackSyncTime");
        });
        client.addLastAction(()->{
            client.writeAndFlush(GpcCall.newBuilder()
                    .setMethod("onSyncTime")
                    .setParams(GpcVoid.getDefaultInstance().toByteString())
                    .build());
            client.waitForCallback("callbackSyncTime");
        });
        client.connect("127.0.0.1", 9005);
        while (true) {
            client.doAction();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
