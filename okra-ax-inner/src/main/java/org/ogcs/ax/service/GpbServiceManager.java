package org.ogcs.ax.service;

import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.DescriptorProtos.ServiceOptions;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.ax.command.AxCommand;
import org.ogcs.ax.command.ClientCommand;
import org.ogcs.ax.component.core.AxService;
import org.ogcs.ax.component.exception.RegisteredException;
import org.ogcs.ax.component.exception.UndefinedException;
import org.ogcs.ax.component.exception.UnknownCmdException;
import org.ogcs.ax.gpb3.AxOptions;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
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
    private Map<Integer, Command> commands = new HashMap<>();
    private ExtensionRegistryLite extensionRegistry = ExtensionRegistry.getEmptyRegistry();

    /**
     *
     * @param serviceId
     * @return
     * @throws UndefinedException
     */
    public AxService getService(int serviceId) throws UndefinedException {
        if (services.containsKey(serviceId)) {
            return services.get(serviceId);
        } else {
            throw new UndefinedException("Service undefined. : " + serviceId);
        }
    }

    /**
     * Get the command instance.
     */
    public Command interpret(int cmd) throws UnknownCmdException {
        if (commands.containsKey(cmd)) {
            return commands.get(cmd);
        } else {
            throw new UnknownCmdException("Unknown command : " + cmd);
        }
    }

    public void registerService(Class clzOfGpb) throws Exception {
        Method mtdGetDescriptor = clzOfGpb.getDeclaredMethod("getDescriptor");
        FileDescriptor fileDescriptor = (FileDescriptor) mtdGetDescriptor.invoke(null);
        //  register extensions
        Method mtdRegExtensions = clzOfGpb.getDeclaredMethod("registerAllExtensions", ExtensionRegistryLite.class);
        mtdRegExtensions.invoke(null, extensionRegistry);
        //  register all services
        for (ServiceDescriptor serviceDescriptor : fileDescriptor.getServices()) {
            ServiceOptions serviceOptions = serviceDescriptor.getOptions();
            String clzOfJavaService = serviceOptions.getExtension(AxOptions.serviceRef);
            if (clzOfJavaService.isEmpty()) {
                throw new UndefinedException("Option [serviceRef] is undefined.");
            }
            Class<?> clzOfImpl = Class.forName(clzOfJavaService);
            if (!AxService.class.isAssignableFrom(clzOfImpl)) {
                throw new IllegalStateException("The class [" + clzOfImpl.getName() + "] is not AxService.");
            }
            Integer serviceId = serviceOptions.getExtension(AxOptions.serviceId);
            Boolean isPublic = serviceOptions.getExtension(AxOptions.isPublic);
            if (services.containsKey(serviceId)) {
                throw new RegisteredException("The service is registered.");
            }
            AxService service = (AxService) clzOfImpl.newInstance();
            service.setId(serviceId).setPublic(isPublic);
            //  register all methods
            Method[] methods = clzOfImpl.getDeclaredMethods();
            for (Method mtdApi : methods) {
                MethodDescriptor methodByName = serviceDescriptor.findMethodByName(mtdApi.getName());
                if (methodByName == null) {
                    throw new UndefinedException("Service method [" + mtdApi.getName() + "] is undefined.");
                }
                MethodOptions options = methodByName.getOptions();
                if (!options.hasExtension(AxOptions.methodId)) {
                    throw new UndefinedException("Option [methodId] is undefined.");
                }
                Integer methodId = options.getExtension(AxOptions.methodId);
                if (commands.containsKey(methodId)) {
                    throw new RegisteredException("The command code [ " + methodId + " ] is registered.");
                }
                //  TODO: 第二个参数是Message
                Class<?> type = mtdApi.getParameters()[1].getType();
                Method mtdParseFrom = type.getDeclaredMethod("parseFrom", byte[].class, ExtensionRegistryLite.class);

                commands.put(methodId, newCommand(methodId, service, mtdApi, mtdParseFrom, extensionRegistry));
            }
            services.put(service.id(), service);
        }
    }

    protected Command newCommand(int id, AxService service, Method mtdApi, Method mtdReqParseFrom, ExtensionRegistryLite extensionRegistry) {
        if (service.isPublic()) {
            return new ClientCommand(id, service, mtdApi, mtdReqParseFrom, extensionRegistry);
        } else {
            return new AxCommand(id, service, mtdApi, mtdReqParseFrom, extensionRegistry);
        }
    }

}
