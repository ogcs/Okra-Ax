package org.ogcs.ax.service;

import org.ogcs.ax.component.core.AxService;

/**
 * @author TinyZ
 * @date 2016-10-20.
 */
public abstract class AbstractServiceImpl implements AxService {
    
    private int id;
    private Class<?> clzOfGpb;

    public AbstractServiceImpl(int id, Class<?> clzOfGpb) {
        this.id = id;
        this.clzOfGpb = clzOfGpb;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Class<?> getGpbService() {
        return clzOfGpb;
    }

}
