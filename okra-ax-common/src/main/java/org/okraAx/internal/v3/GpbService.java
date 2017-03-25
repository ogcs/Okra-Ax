package org.okraAx.internal.v3;

import com.google.protobuf.Descriptors.ServiceDescriptor;
import org.okraAx.internal.core.AxService;

/**
 * @author TinyZ
 * @date 2016-10-16.
 * @since 1.0
 */
public interface GpbService extends AxService {

    /**
     * @return Return GPB service rpc's proto file's descriptor.
     */
    ServiceDescriptor desc();
    /**
     * @return Return true if the server is public. otherwise false.
     */
    boolean isPublic();

    /**
     * 对应的Gpb服务
     *
     * @return 返回对应的Gpb的Service
     */
    Class<?> getGpbService();

    GpbService setId(int id);

    GpbService setPublic(boolean aPublic);

    GpbService setClzOfGpb(Class<?> clzOfGpb);

}
