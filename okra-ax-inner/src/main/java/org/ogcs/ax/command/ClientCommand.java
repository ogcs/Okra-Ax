package org.ogcs.ax.command;

import com.google.protobuf.ExtensionRegistryLite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.ax.component.core.AxService;
import org.ogcs.ax.gpb3.GpbD.Request;

import java.lang.reflect.Method;

/**
 * @author TinyZ
 * @date 2016-10-21.
 */
public class ClientCommand implements Command<Session, Request> {

    private static final Logger LOG = LogManager.getLogger(AxCommand.class);
    private int id;
    private AxService service;
    private Method mtdApi;
    private Method mtdReqParseFrom;
    private ExtensionRegistryLite extensionRegistry;

    public ClientCommand(int id, AxService service, Method mtdApi, Method mtdReqParseFrom, ExtensionRegistryLite extensionRegistry) {
        this.id = id;
        this.service = service;
        this.mtdApi = mtdApi;
        this.mtdReqParseFrom = mtdReqParseFrom;
        this.extensionRegistry = extensionRegistry;
    }

    @Override
    public void execute(Session session, Request request) throws Exception {
        try {
            Object msg = mtdReqParseFrom.invoke(null, request.getData().toByteArray(), extensionRegistry);
            mtdApi.invoke(service, session, request, msg);
        } catch (Exception e) {
            LOG.error("Command inbound error.", e);
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
