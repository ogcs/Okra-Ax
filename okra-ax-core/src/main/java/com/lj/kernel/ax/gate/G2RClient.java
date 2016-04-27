package com.lj.kernel.ax.gate;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.ConnectorManager;
import com.lj.kernel.ax.core.GpbClient;
import com.lj.kernel.gpb.generated.GpbD;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.GpbD.Outbound;
import com.lj.kernel.gpb.generated.GpbD.Request;
import com.lj.kernel.gpb.generated.Remote;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.ogcs.app.AppContext;

import java.util.concurrent.atomic.AtomicInteger;

import static com.lj.kernel.ax.HandlerConst.GPB_OUTBOUND_DECODER;

/**
 * Gate connect to Remote
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class G2RClient extends GpbClient<Outbound> {

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private RemoteManager remoteManager = (RemoteManager) AppContext.getBean(SpringContext.MANAGER_REMOTE);

    private final String remoteId;
    private final String gateId;

    public G2RClient(String gateId, String remoteId, String host, int port) {
        super(host, port, true);
        this.remoteId = remoteId;
        this.gateId = gateId;
    }

    public String id() {
        return remoteId;
    }

    @Override
    public void addGpbDecoder(ChannelPipeline cp) {
        cp.addLast("gpbDecoder", GPB_OUTBOUND_DECODER);
    }

    @Override
    public void connectionActive(ChannelHandlerContext ctx) {
        super.connectionActive(ctx);
        // 注册
        Remote.ReqRemoteAuth build = Remote.ReqRemoteAuth.newBuilder()
                .setAuth("ABCD")
                .setId(gateId)
                .build();
        session.writeAndFlush(Inbound.newBuilder()
                .setId(REQUEST_ID.getAndIncrement())
                .setUid(-1)
                .setMethod(29999)
                .setData(build.toByteString())
                .build());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Outbound msg) {
        byte[] bytes = msg.getData().toByteArray();
        if (msg.getUidsCount() > 0) {
            connectorManager.pushById(Unpooled.wrappedBuffer(bytes), msg.getUidsList().toArray());
        } else {
            connectorManager.pushAll(Unpooled.wrappedBuffer(bytes));
        }
        System.out.println(msg.toString());
    }

    @Override
    public void connectionInactive(ChannelHandlerContext ctx) throws Exception {
        super.connectionInactive(ctx);
    }
}
