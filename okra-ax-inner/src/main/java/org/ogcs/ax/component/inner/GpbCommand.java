package org.ogcs.ax.component.inner;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;

import java.lang.reflect.Method;

/**
 * @author TinyZ
 * @date 2016-10-16.
 */
public class GpbCommand {

    private ServiceDescriptor serviceDescriptor;
    private MethodDescriptor methodDescriptor;
    private DescriptorProto inputParamType;
    private Method method;
    private Class clzOfGpb;
    private Method parseFrom;

    public GpbCommand(ServiceDescriptor serviceDescriptor, MethodDescriptor methodDescriptor,
                      DescriptorProto inputParamType, Method method) {
        this.serviceDescriptor = serviceDescriptor;
        this.methodDescriptor = methodDescriptor;
        this.inputParamType = inputParamType;
        this.method = method;
    }

    public ServiceDescriptor getServiceDescriptor() {
        return serviceDescriptor;
    }

    public void setServiceDescriptor(ServiceDescriptor serviceDescriptor) {
        this.serviceDescriptor = serviceDescriptor;
    }

    public MethodDescriptor getMethodDescriptor() {
        return methodDescriptor;
    }

    public void setMethodDescriptor(MethodDescriptor methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    public DescriptorProto getInputParamType() {
        return inputParamType;
    }

    public void setInputParamType(DescriptorProto inputParamType) {
        this.inputParamType = inputParamType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
