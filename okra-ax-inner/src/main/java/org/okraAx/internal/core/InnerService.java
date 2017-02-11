package org.okraAx.internal.core;

/**
 * @author TinyZ
 * @date 2016-10-16.
 * @since 1.0
 */
public interface InnerService extends AxService {

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

    InnerService setId(int id);

    InnerService setPublic(boolean aPublic);

    InnerService setClzOfGpb(Class<?> clzOfGpb);
}
