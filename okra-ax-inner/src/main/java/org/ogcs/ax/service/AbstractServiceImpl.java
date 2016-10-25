package org.ogcs.ax.service;

import org.ogcs.ax.component.core.AxServiceImpl;

/**
 * @author TinyZ
 * @date 2016-10-20.
 */
public abstract class AbstractServiceImpl implements AxServiceImpl {

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
    public AxServiceImpl setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public AxServiceImpl setPublic(boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    @Override
    public AxServiceImpl setClzOfGpb(Class<?> clzOfGpb) {
        this.clzOfGpb = clzOfGpb;
        return this;
    }
}
