package org.okraAx.login.bean;

import org.okraAx.internal.core.Changeable;

/**
 * @author TinyZ.
 * @version 2017.07.20
 */
public abstract class ChangeableBean<K> implements Changeable {

    private volatile boolean isChanged = false;

    /**
     */
    public abstract K beanKey();

    @Override
    public boolean isChanged() {
        return isChanged;
    }

    @Override
    public void setChanged(boolean status) {
        this.isChanged = status;
    }
}
