package org.ogcs.ax;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.MessageLite;
import org.junit.Test;
import org.ogcs.app.Session;
import org.ogcs.ax.component.service.impl.AxLogicServiceImpl;
import org.ogcs.ax.component.inner.GpbCommand;
import org.ogcs.ax.utilities.GpbUtil;
import org.ogcs.gpb.ax.NonGenericService;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2016-10-16.
 */
public class GpbServiceLoaderTest {

    @Test
    public void testLoadGpbService() throws Exception {
        Map<Integer, GpbCommand> map = GpbUtil.loadGpbService(NonGenericService.class, AxLogicServiceImpl.class);
        DescriptorProto descriptorProto = map.get(998).getInputParamType();
        NonGenericService.OnConnectAuth build = NonGenericService.OnConnectAuth.newBuilder()
                .setNum(4567)
                .build();
        MessageLite msg = descriptorProto.newBuilderForType()
                .mergeFrom(build.toByteArray())
                .build();

        String name = descriptorProto.getName();



//        Assert.assertEquals();
        System.out.println();
    }

    @Test
    public void testInvoke() throws Exception {
        NonGenericService.OnConnectAuth build = NonGenericService.OnConnectAuth.newBuilder()
                .setNum(4567)
                .build();
        //
        Map<Integer, GpbCommand> map = GpbUtil.loadGpbService(NonGenericService.class, AxLogicServiceImpl.class);
        GpbCommand cmd = map.get(998);

        MessageLite descriptorProto = cmd.getInputParamType().getParserForType().parseFrom(build.toByteArray());

        MessageLite msg = cmd.getInputParamType().newBuilderForType().mergeFrom(build.toByteArray()).build();
        AxLogicServiceImpl service = new AxLogicServiceImpl();

//        service.auth(null, NonGenericService.OnConnectAuth.parseFrom(build.toByteArray()));

        Method cmdMethod = cmd.getMethod();
        Class<?>[] parameterTypes = cmdMethod.getParameterTypes();
        Method parseFrom = parameterTypes[1].getDeclaredMethod("parseFrom", byte[].class);
        cmdMethod.invoke(service, null, parseFrom.invoke(parameterTypes[1], build.toByteArray()));

        Method method = service.getClass().getMethod(cmd.getMethodDescriptor().getName(), Session.class, MessageLite.class);//, Session.class, MessageLite.class
        method.invoke(service, null, msg);

        System.out.println();
    }












}
