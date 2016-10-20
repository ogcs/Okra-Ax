package org.ogcs.ax.component.core;

/**
 * @author TinyZ
 * @date 2016-10-16.
 * @since 1.0
 */
public interface AxService {

    /**
     * 获取服务的注册ID
     *
     * @return 返回服务的注册ID
     */
    int id();

    /**
     * 对应的Gpb服务
     *
     * @return 返回对应的Gpb的Service
     */
    Class<?> getGpbService();
}
