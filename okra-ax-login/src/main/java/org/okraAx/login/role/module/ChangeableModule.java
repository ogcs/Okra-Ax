package org.okraAx.login.role.module;

import org.okraAx.internal.core.Changeable;
import org.okraAx.login.role.Modules;

/**
 * @author TinyZ.
 * @version 2017.07.20
 */
public abstract class ChangeableModule
        extends AbstractModule implements Changeable {
    protected volatile boolean isChanged = false;

    public ChangeableModule(Modules modules) {
        super(modules);
    }

    /**
     * Is module' data changed.
     *
     * @return return true if the module's data is changed, otherwise false.
     */
    @Override
    public boolean isChanged() {
        return this.isChanged;
    }

    @Override
    public void setChanged(boolean changed) {
        isChanged = changed;
    }

}
