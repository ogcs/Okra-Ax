package org.ogcs.ax.component.inner;

import com.google.protobuf.ExtensionRegistryLite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Session;
import org.ogcs.ax.component.core.AxService;
import org.ogcs.ax.gpb.OkraAx.AxInbound;

import java.lang.reflect.Method;

/**
 * @author TinyZ
 * @date 2016-10-17.
 */
public class GpbCommand {

    private static final Logger LOG = LogManager.getLogger(GpbCommand.class);
    private int id;
    private AxService service;
    private Method mtdApi;
    private Method mtdParseFrom;
    private ExtensionRegistryLite extensionRegistry;

    public GpbCommand(int id, AxService service, Method mtdApi, Method mtdParseFrom, ExtensionRegistryLite extensionRegistry) {
        this.id = id;
        this.service = service;
        this.mtdApi = mtdApi;
        this.mtdParseFrom = mtdParseFrom;
        this.extensionRegistry = extensionRegistry;
    }

    public void execute(Session session, AxInbound inBound) {
        try {
            Object msg = mtdParseFrom.invoke(null, inBound.toByteArray(), extensionRegistry);
            mtdApi.invoke(service, session, msg);
        } catch (Exception e) {
            LOG.error("Command execute error.", e);
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

    public Method getMtdParseFrom() {
        return mtdParseFrom;
    }

    public void setMtdParseFrom(Method mtdParseFrom) {
        this.mtdParseFrom = mtdParseFrom;
    }

    public ExtensionRegistryLite getExtensionRegistry() {
        return extensionRegistry;
    }

    public void setExtensionRegistry(ExtensionRegistryLite extensionRegistry) {
        this.extensionRegistry = extensionRegistry;
    }
}
