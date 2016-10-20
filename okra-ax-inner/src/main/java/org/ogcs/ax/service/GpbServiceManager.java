package org.ogcs.ax.service;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.ax.component.core.AxService;
import org.ogcs.ax.component.exception.CmdRegisteredException;
import org.ogcs.ax.component.exception.UnknownCmdException;
import org.ogcs.ax.component.inner.GpbCommand;
import org.ogcs.ax.utilities.GpbUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2016-10-19.
 * @since 1.0
 */
@Service("GpbServiceManager")
public final class GpbServiceManager {

    private static final Logger LOG = LogManager.getLogger(GpbServiceManager.class);
    private Map<Integer, AxService> services = new HashMap<>();
    private Map<Integer, GpbCommand> commands = new HashMap<>();
    private ExtensionRegistryLite extensionRegistry = ExtensionRegistry.getEmptyRegistry();

    /**
     * Get the command instance.
     */
    public GpbCommand interpret(int cmd) throws UnknownCmdException {
        if (commands.containsKey(cmd)) {
            return commands.get(cmd);
        } else {
            throw new UnknownCmdException("Unknown command : " + cmd);
        }
    }

    /**
     * Register service.
     *
     * @param service 服务
     */
    public void register(AxService service) throws Exception {
        if (services.containsKey(service.id())) {
            LOG.error("The service is registered.");
            return;
        }
        Map<Integer, GpbCommand> regCmdMap = GpbUtil.register(service, service.getGpbService(), extensionRegistry);
        for (Map.Entry<Integer, GpbCommand> entry : regCmdMap.entrySet()) {
            if (commands.containsKey(entry.getKey())) {
                throw new CmdRegisteredException("The command code [ " + entry.getKey() + " ] is registered.");
            }
            commands.put(entry.getKey(), entry.getValue());
        }
        services.put(service.id(), service);
    }


}
