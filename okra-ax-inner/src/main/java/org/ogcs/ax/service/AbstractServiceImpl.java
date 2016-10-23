package org.ogcs.ax.service;

import org.ogcs.ax.component.core.AxService;

/**
 * @author TinyZ
 * @date 2016-10-20.
 */
public abstract class AbstractServiceImpl implements AxService {

    private Class<?> clzOfGpb;
    private int id;
    private boolean isPublic;

    @Override
    public int id() {
        return id;
    }

    @Override
    public boolean isPublic() {
        return isPublic;
    }

    @Override
    public Class<?> getGpbService() {
        return clzOfGpb;
    }

    @Override
    public AxService setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public AxService setPublic(boolean aPublic) {
        isPublic = aPublic;
        return this;
    }

    @Override
    public AxService setClzOfGpb(Class<?> clzOfGpb) {
        this.clzOfGpb = clzOfGpb;
        return this;
    }
}
