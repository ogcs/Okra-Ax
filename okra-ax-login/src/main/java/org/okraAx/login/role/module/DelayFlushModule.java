package org.okraAx.login.role.module;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.service.SimpleTaskService;
import org.okraAx.login.component.LoginComponent;
import org.okraAx.login.role.Modules;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author TinyZ.
 * @version 2017.07.20
 */
public abstract class DelayFlushModule extends AbstractModule {

    private static final Logger LOG = LogManager.getLogger(DelayFlushModule.class);

    protected volatile boolean isChanged = false;

    public DelayFlushModule(Modules modules) {
        super(modules);
    }

    /**
     * Is module' data changed.
     * @return return true if the module's data is changed, otherwise false.
     */
    public boolean isChanged() {
        return this.isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
