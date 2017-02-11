package org.okraAx.component.command;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.ExtensionRegistryLite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.okraAx.internal.core.InnerService;
import org.okraAx.v3.OkraAx.AxInbound;
import org.okraAx.v3.OkraAx.AxOutbound;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 *
 * <pre>通过Gpb的{@link DynamicMessage}实现动态解析message</pre>
 * @author TinyZ
 * @date 2016-10-17.
 */
public class DynamicGpbCommand implements Command<Session, AxInbound> {

    private static final Logger LOG = LogManager.getLogger(DynamicGpbCommand.class);
    private int id;
    private InnerService service;
    private Method mtdApi;
    private Descriptor input;
    private Descriptor output;
    private ExtensionRegistryLite extensionRegistry;

    public DynamicGpbCommand(int id, InnerService service, Method mtdApi, Descriptor input, Descriptor output, ExtensionRegistryLite extensionRegistry) {
        this.id = id;
        this.service = service;
        this.mtdApi = mtdApi;
        this.input = input;
        this.output = output;
        this.extensionRegistry = extensionRegistry;
    }

    @Override
    public void execute(Session session, AxInbound request) throws Exception {
        try {
            DynamicMessage message = DynamicMessage
                    .newBuilder(this.input)
                    .mergeFrom(request.getData(), extensionRegistry)
                    .build();
            Collection<Object> values = message.getAllFields().values();
            Object[] args = new Object[values.size() + 1];
            args[0] = session;
            System.arraycopy(values.toArray(), 0, args, 1, values.size());
            mtdApi.invoke(service, args);
        } catch (Exception e) {
            LOG.error("Command inbound error.", e);
        }
    }

    public void outbound(Session session, AxOutbound outbound) {
//        try {
//            Object msg = mtdResParseFrom.invoke(null, outbound.toByteArray(), extensionRegistry);
//            mtdApi.invoke(service, session, msg);
//        } catch (Exception e) {
//            LOG.error("Command inbound error.", e);
//        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InnerService getService() {
        return service;
    }

    public void setService(InnerService service) {
        this.service = service;
    }

    public Method getMtdApi() {
        return mtdApi;
    }

    public void setMtdApi(Method mtdApi) {
        this.mtdApi = mtdApi;
    }

    public ExtensionRegistryLite getExtensionRegistry() {
        return extensionRegistry;
    }

    public void setExtensionRegistry(ExtensionRegistryLite extensionRegistry) {
        this.extensionRegistry = extensionRegistry;
    }
}
