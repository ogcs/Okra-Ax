package org.okraAx.internal.v3;

import com.google.protobuf.Descriptors.ServiceDescriptor;

/**
 * @author TinyZ
 * @since 1.1
 */
public abstract class AbstractGpbService implements GpbService {

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
    public GpbService setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public GpbService setPublic(boolean aPublic) {
        this.isPublic = aPublic;
        return this;
    }

    @Override
    public GpbService setClzOfGpb(Class<?> clzOfGpb) {
        this.clzOfGpb = clzOfGpb;
        return this;
    }
}
