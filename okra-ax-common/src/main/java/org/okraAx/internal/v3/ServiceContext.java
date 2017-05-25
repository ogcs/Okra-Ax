package org.okraAx.internal.v3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.05.22
 */
public abstract class ServiceContext {

    private static final Logger LOG = LogManager.getLogger(ServiceContext.class);

    /**
     * Command factory.
     */
    private CmdFactory cmdFactory;
    //  command map
    private final Map<String, Command> methods = new ConcurrentHashMap<>();

    public <T> ServiceContext registerService(T obj, Class<T> service) {
        if (obj == null) throw new NullPointerException("obj");
        if (service == null) throw new NullPointerException("service");
        for (Method method : service.getDeclaredMethods()) {
            if (methods.containsKey(method.getName())) {
                LOG.info("[ServerContext] the method name is registered. method name : {}", method.getName());
            }
            Command cmd = cmdFactory.newCommand(obj, method);
            if (cmd == null) {
                LOG.error("[ServerContext] factory create command error.");
            }
            methods.put(method.getName(), cmd);
        }
        return this;
    }

    public Command getMethod(String methodName) {
        return methods.get(methodName);
    }

    public ServiceContext initCmdFactory(final CmdFactory factory) {
        this.cmdFactory = factory;
        return this;
    }

    public ServiceContext build() {
        return this;
    }
}