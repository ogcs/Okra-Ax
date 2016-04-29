package com.lj.kernel.ax.inner;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.AxComponent;
import com.lj.kernel.ax.core.ConnectorManager;
import com.lj.kernel.ax.core.GpbClient;
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
public class AxInnerClient extends GpbClient<AxOutbound> implements AxComponent {

    private static final Logger LOG = LogManager.getLogger(AxInnerClient.class);
    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private AxInnerCoManager axCoManager = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);

    private final String module;
    private final long id;
    private final long local;

    public AxInnerClient(String module, long id, long local, String host, int port) {
        super(host, port, true);
        this.module = module;
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
                        .setCmd(1000) // 授权  INNER_AUTH
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
        // 移除
        AxInnerCoShard axCoShard = axCoManager.getAxCoShard(module);
        if (axCoShard != null) {
            axCoManager.removeByModule(module, String.valueOf(id));
        }
        super.connectionInactive(ctx);
    }
}
