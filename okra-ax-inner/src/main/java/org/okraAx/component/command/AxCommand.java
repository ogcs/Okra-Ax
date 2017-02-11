package org.okraAx.component.command;

import com.google.protobuf.ExtensionRegistryLite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.okraAx.internal.core.InnerService;
import org.okraAx.v3.OkraAx.AxInbound;
import org.okraAx.v3.OkraAx.AxOutbound;

import java.lang.reflect.Method;

/**
 * @author TinyZ
 * @date 2016-10-17.
 */
public class AxCommand implements Command<Session, AxInbound> {

    private static final Logger LOG = LogManager.getLogger(AxCommand.class);
    private int id;
    private InnerService service;
    private Method mtdApi;
    private Method mtdReqParseFrom;
    private Method mtdResParseFrom;
    private ExtensionRegistryLite extensionRegistry;

    public AxCommand(int id, InnerService service, Method mtdApi, Method mtdReqParseFrom, ExtensionRegistryLite extensionRegistry) {
        this.id = id;
        this.service = service;
        this.mtdApi = mtdApi;
        this.mtdReqParseFrom = mtdReqParseFrom;
        this.mtdResParseFrom = mtdReqParseFrom;
        this.extensionRegistry = extensionRegistry;
    }

    @Override
    public void execute(Session session, AxInbound request) throws Exception {
        try {
            Object msg = mtdReqParseFrom.invoke(null, request.getData().toByteArray(), extensionRegistry);
            mtdApi.invoke(service, session, request, msg);
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

    public Method getMtdReqParseFrom() {
        return mtdReqParseFrom;
    }

    public void setMtdReqParseFrom(Method mtdReqParseFrom) {
        this.mtdReqParseFrom = mtdReqParseFrom;
    }

    public ExtensionRegistryLite getExtensionRegistry() {
        return extensionRegistry;
    }

    public void setExtensionRegistry(ExtensionRegistryLite extensionRegistry) {
        this.extensionRegistry = extensionRegistry;
    }
}
