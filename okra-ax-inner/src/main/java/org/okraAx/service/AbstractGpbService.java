package org.okraAx.service;

import org.okraAx.internal.core.InnerService;

/**
 * @author TinyZ
 * @since 1.1
 */
public abstract class AbstractGpbService implements InnerService {

    private int id;
    private boolean isPublic;
    private Class<?> clzOfGpb;

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
    public InnerService setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public InnerService setPublic(boolean aPublic) {
        this.isPublic = aPublic;
        return this;
    }

    @Override
    public InnerService setClzOfGpb(Class<?> clzOfGpb) {
        this.clzOfGpb = clzOfGpb;
        return this;
    }
}
