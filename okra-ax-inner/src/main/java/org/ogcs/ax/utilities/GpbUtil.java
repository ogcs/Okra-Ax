package org.ogcs.ax.utilities;

import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.ExtensionRegistryLite;
import org.ogcs.ax.component.core.AxService;
import org.ogcs.ax.component.inner.GpbCommand;
import org.ogcs.ax.gpb3.AxOptions;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2016-10-18.
 */
public class GpbUtil {


//    public static void main(String[] args) throws Exception {
//        ExGpb.SayHello build = ExGpb.SayHello.newBuilder().setCount(2).build();
//        ExtensionRegistryLite extensionRegistry = ExtensionRegistry.newInstance();
//        AxService service = new ExGpbService();
//        //
//        Map<Integer, GpbCommand> console = new HashMap<>();
//        register(ExGpb.class, ExGpbService.class, console, extensionRegistry);
//        //
//        GpbCommand gpbCommand = console.get(1995);
//        gpbCommand.getMtdApi().invoke(service, null, gpbCommand.getMtdParseFrom().invoke(null, build.toByteArray(), gpbCommand.getExtensionRegistry()));
//
//        System.out.println();
//    }

    /**
     * 注册方法
     *
     * @param serviceImpl
     * @param clzOfGpb
     * @param extensionRegistry
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<Integer, GpbCommand> register(AxService serviceImpl, Class clzOfGpb, ExtensionRegistryLite extensionRegistry) throws Exception {
        Map<Integer, GpbCommand> commands = new HashMap<>();
        Method mtdGetDescriptor = clzOfGpb.getDeclaredMethod("getDescriptor");
        FileDescriptor fileDescriptor = (FileDescriptor) mtdGetDescriptor.invoke(null);
        //  register extensions
        Method mtdRegExtensions = clzOfGpb.getDeclaredMethod("registerAllExtensions", ExtensionRegistryLite.class);
        mtdRegExtensions.invoke(null, extensionRegistry);
        for (ServiceDescriptor serviceDescriptor : fileDescriptor.getServices()) {
            Method[] methods = serviceImpl.getClass().getDeclaredMethods();
            for (Method mtdApi : methods) {
                MethodDescriptor methodByName = serviceDescriptor.findMethodByName(mtdApi.getName());
                if (methodByName == null) {
                    throw new Exception("Service method [" + mtdApi.getName() + "] is undefined.");
                }
                MethodOptions options = methodByName.getOptions();
                if (!options.hasExtension(AxOptions.methodId)) {
                    throw new Exception("Option methodId is missing.");
                }
                Integer methodId = options.getExtension(AxOptions.methodId);
                //  第二个参数是Message
                Class<?> type = mtdApi.getParameters()[1].getType();
                Method mtdParseFrom = type.getDeclaredMethod("parseFrom", byte[].class, ExtensionRegistryLite.class);
                commands.put(methodId, new GpbCommand(methodId, serviceImpl, mtdApi, mtdParseFrom, extensionRegistry));
            }
        }
        return commands;
    }
}
