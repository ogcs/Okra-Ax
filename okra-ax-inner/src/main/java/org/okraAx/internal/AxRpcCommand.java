package org.okraAx.internal;

import com.google.protobuf.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.okraAx.internal.core.AxService;
import org.okraAx.utilities.AxAnyUtil;
import org.okraAx.v3.Rpc;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * <pre>通过AxAny的实现动态解析message</pre>
 *
 * @author TinyZ
 * @see com.google.protobuf.Any
 */
public class AxRpcCommand implements Command<Session, Rpc> {

    private static final Logger LOG = LogManager.getLogger(AxRpcCommand.class);
    private int id;
    private AxService service;
    private Method mtdApi;
    private Class<? extends Message> clz;

    public AxRpcCommand(int id, AxService service, Method mtdApi, Class<? extends Message> clz) {
        this.id = id;
        this.service = service;
        this.mtdApi = mtdApi;
        this.clz = clz;
    }

    @Override
    public void execute(Session session, Rpc request) throws Exception {
        try {
            Message message = AxAnyUtil.unpack(request.getData(), this.clz);
            //
            Collection<Object> values = message.getAllFields().values();
            Object[] args = new Object[values.size() + 1];
            args[0] = session;
            if (!values.isEmpty())
                System.arraycopy(values.toArray(), 0, args, 1, values.size());
            mtdApi.invoke(service, args);
        } catch (Exception e) {
            LOG.error("RPC invoke error.", e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AxService getService() {
        return service;
    }

    public void setService(AxService service) {
        this.service = service;
    }
}
