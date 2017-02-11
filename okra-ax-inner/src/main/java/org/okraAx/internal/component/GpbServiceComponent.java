package org.okraAx.internal.component;

import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.DescriptorProtos.ServiceOptions;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.okraAx.v3.AxOptions;
import org.okraAx.internal.AxRpcCommand;
import org.okraAx.internal.core.InnerService;
import org.okraAx.internal.exception.RegisteredException;
import org.okraAx.internal.exception.UndefinedException;
import org.okraAx.internal.exception.UnknownCmdException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TinyZ
 * @since 1.1
 */
@Service
public final class GpbServiceComponent {

    private static final Logger LOG = LogManager.getLogger(GpbServiceComponent.class);
    private Map<Integer, InnerService> services = new HashMap<>();
    private Map<Integer, Command> commands = new HashMap<>();
    private ExtensionRegistryLite extensionRegistry = ExtensionRegistry.getEmptyRegistry();

    /**
     * @param serviceId
     * @return
     * @throws UndefinedException
     */
    public InnerService getService(int serviceId) throws UndefinedException {
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

    @SuppressWarnings("unchecked")
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
            if (!InnerService.class.isAssignableFrom(clzOfImpl)) {
                throw new IllegalStateException("The class [" + clzOfImpl.getName() + "] is not IrService.");
            }
            Integer serviceId = serviceOptions.getExtension(AxOptions.serviceId);
            Boolean isPublic = serviceOptions.getExtension(AxOptions.isPublic);
            if (services.containsKey(serviceId)) {
                throw new RegisteredException("The service is registered.");
            }
            InnerService service = (InnerService) clzOfImpl.newInstance();
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
                Class<? extends Message> clzOfMsg = lookupClzOfMsg(methodByName);
                commands.put(methodId, new AxRpcCommand(methodId, service, mtdApi, clzOfMsg));
            }
            services.put(service.id(), service);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Message> lookupClzOfMsg(MethodDescriptor methodDescriptor) throws ClassNotFoundException {
        String fullName = methodDescriptor.getInputType().getFullName();
        try {
            return (Class<? extends Message>) Class.forName(fullName);
        } catch (ClassNotFoundException e) {
            String clzFullName = methodDescriptor.getInputType().getOptions().getExtension(AxOptions.messageRef);
            return (Class<? extends Message>) Class.forName(clzFullName);
        }
    }



}
