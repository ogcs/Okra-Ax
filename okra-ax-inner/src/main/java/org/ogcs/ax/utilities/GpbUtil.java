package org.ogcs.ax.utilities;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.ax.component.inner.GpbCommand;
import org.ogcs.gpb.ax.OptionsId;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gpb service 解析加载工具
 *
 * @author TinyZ
 */
public final class GpbUtil {

    private static final Logger LOG = LogManager.getLogger(GpbUtil.class);

    /**
     * 加载Gpb解析service
     * @return 返回
     * @throws IllegalStateException
     */
    public static Map<Integer, GpbCommand> loadGpbService(Class clzOfGpb, Class clzOfImpl) throws IllegalStateException {
        FileDescriptor fileDescriptor = getFileDescriptor(clzOfGpb);
        if (fileDescriptor == null)
            return null;
        Map<Integer, GpbCommand> map = new HashMap<>();
        List<ServiceDescriptor> services = fileDescriptor.getServices();
        for (ServiceDescriptor service : services) {
            Map<String, MethodDescriptor> methodMap = service
                    .getMethods()
                    .stream()
                    .collect(Collectors.toMap(MethodDescriptor::getName, (value) -> value));
            Method[] methods = clzOfImpl.getDeclaredMethods();
            for (Method method : methods) {
                MethodDescriptor methodDescriptor = methodMap.get(method.getName());
                DescriptorProto inputParamType = methodDescriptor.getInputType().toProto();
                MethodOptions options = methodDescriptor.getOptions();
                Integer methodId = options.getExtension(OptionsId.methodId);
                if (methodId <= 0) {
                    throw new IllegalStateException("The method's methodId is missing. [service: " + service.getFullName() +
                            ", method" + methodDescriptor.getFullName() + "]");
                }
                map.put(methodId, new GpbCommand(service, methodDescriptor, inputParamType, method));
            }
        }
        return map;
    }

    public static FileDescriptor getFileDescriptor(Class clz) {
        try {
            Method descriptor = clz.getDeclaredMethod("getDescriptor");

            return (FileDescriptor) descriptor.invoke(null);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {

        }
        return null;
    }
}
