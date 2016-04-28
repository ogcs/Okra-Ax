package com.lj.kernel.ax.inner;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.AxComponent;
import com.lj.kernel.ax.core.ConnectorManager;
import com.lj.kernel.ax.core.GpbClient;
import com.lj.kernel.ax.gate.RemoteManager;
import com.lj.kernel.gpb.OkraAx.AxInbound;
import com.lj.kernel.gpb.OkraAx.AxOutbound;
import com.lj.kernel.gpb.OkraAx.AxReqAuth;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;

import static com.lj.kernel.ax.HandlerConst.AX_OUTBOUND_DECODER;

/**
 * Gate connect to Remote
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class AxClient extends GpbClient<AxOutbound> implements AxComponent {

    private static final Logger LOG = LogManager.getLogger(AxClient.class);

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private final long id;
    private final long local;

    /**
     * @param id    client's id
     * @param local manager's id
     * @param host  remote host
     * @param port  remote port
     */
    public AxClient(long id, long local, String host, int port) {
        super(host, port, true);
        this.local = local;
        this.id = id;
    }

    @Override
    public String id() {
        return String.valueOf(id);
    }

    @Override
    public void addGpbDecoder(ChannelPipeline cp) {
        cp.addLast("axOutboundDecoder", AX_OUTBOUND_DECODER);
    }

    @Override
    public void connectionActive(ChannelHandlerContext ctx) {
        super.connectionActive(ctx);
        // 验证访问授权
        session.writeAndFlush(
                AxInbound.newBuilder()
                        .setRid(REQUEST_ID.getAndIncrement())
                        .setCmd(29999)
                        .setSource(local)
                        .setData(
                                AxReqAuth.newBuilder()
                                        .setKey("ABCD")
                                        .setSource(local)
                                        .build().toByteString()
                        )
                        .build()
        );
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, AxOutbound msg) {
        if (msg.hasError()) {
            LOG.warn("Ax Inner Exception [ " + msg.getError().getState() + "] : " + msg.getError().getMsg());
        } else {
            byte[] bytes = msg.getData().toByteArray();
            if (msg.getTargetCount() > 0) {
                connectorManager.pushById(Unpooled.wrappedBuffer(bytes), msg.getTargetList().toArray());
            } else {
                connectorManager.pushAll(Unpooled.wrappedBuffer(bytes));
            }
            System.out.println(msg.toString());
        }
    }

    @Override
    public void connectionInactive(ChannelHandlerContext ctx) throws Exception {
        super.connectionInactive(ctx);
    }
}
