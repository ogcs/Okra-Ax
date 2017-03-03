package org.okraAx.internal.v3;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.internal.exception.UnknownCmdException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2017-02-28.
 */
@Component
public class GpbMethodComponent {

    private static final Logger LOG = LogManager.getLogger(GpbMethodComponent.class);
    /**
     *
     */
    private final Map<String, GpbMethodDesc> methodDescMap = new HashMap<>();
    private final Map<String, GpbCommand> methods = new HashMap<>();

    public GpbMethodDesc getMethodDescriptor(String methodName) {
        return this.methodDescMap.get(methodName);
    }

    public void register(List<ServiceDescriptor> services) {
        for (ServiceDescriptor serviceDescriptor : services) {
            register(serviceDescriptor);
        }
    }

    public void register(ServiceDescriptor serviceDescriptor) {
        for (MethodDescriptor methodDescriptor : serviceDescriptor.getMethods()) {
            if (methodDescMap.containsKey(methodDescriptor.getName())) {
                LOG.warn("[Gpb] re-register method [" + methodDescriptor.getName() + "].");
            }
            methodDescMap.put(methodDescriptor.getName(), new GpbMethodDesc(methodDescriptor));
        }
    }

    public void registerMethod(Object obj, Class<?> clzOfService) {
        if (!clzOfService.isAssignableFrom(obj.getClass())) {
            throw new IllegalStateException("[Gpb] Class [" + obj.getClass().getName()
                    + "] is not assignable from " + clzOfService.getName());
        }
        if (obj instanceof GpbService) {
            ServiceDescriptor serviceDescriptor = ((GpbService) obj).getDescriptor();
            for (Method method : clzOfService.getDeclaredMethods()) {
                MethodDescriptor methodDescriptor = serviceDescriptor.findMethodByName(method.getName());
                if (methodDescriptor == null)
                    throw new IllegalStateException("[Gpb] Undefined method [" + method.getName() + "].");
                GpbMethodDesc methodDesc = new GpbMethodDesc(methodDescriptor);
                methodDescMap.put(method.getName(), methodDesc);
                GpbCommand command = new GpbCommand(obj, method, methodDesc);
                methods.put(method.getName(), command);
            }
        }
    }

    public GpbCommand interpret(String method) throws UnknownCmdException {
        if (methods.containsKey(method)) {
            return methods.get(method);
        } else {
            throw new UnknownCmdException("Unknown command : " + method);
        }
    }




}
