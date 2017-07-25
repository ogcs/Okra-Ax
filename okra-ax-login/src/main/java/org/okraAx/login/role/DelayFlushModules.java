package org.okraAx.login.role;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.service.SimpleTaskService;
import org.okraAx.internal.core.Changeable;
import org.okraAx.login.role.module.Module;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 * 定时刷脏数据的模块.
 * <pre>
 *     1. 使用ScheduledThreadPoolExecutor实现定时检查是否有脏数据，并提交落地任务.
 *     2. 使用ForkJoinPool共享线程池来实现并行脏数据写入落地.
 *     3. 以单个用户为基础单位, 玩家数据变更往往是多个模块联合变动, 有效避免flush任务过多.
 * </pre>
 *
 * @author TinyZ.
 * @version 2017.07.20
 */
public abstract class DelayFlushModules implements Modules {

    private static final Logger LOG = LogManager.getLogger(DelayFlushModules.class);

    private static final SimpleTaskService SCHEDULED_SERVICE = new SimpleTaskService();

    private static final ForkJoinPool WORKERS = new ForkJoinPool(8);

    /**
     * 挂载的模块
     */
    protected final Map<Class<? extends Module>, Module> modules = new ConcurrentHashMap<>();

    protected volatile ForkJoinTask<?> task;

    public DelayFlushModules() {
        //  Scheduled Task
        SCHEDULED_SERVICE.scheduleWithFixedDelay(() -> {
            try {
                if (this.task.isDone()) {
                    this.task = WORKERS.submit(this::delayFlushToDB);
                }
            } catch (Exception e) {
                LOG.error("modules:" + modules, e);
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    private void delayFlushToDB() {
        for (Map.Entry<Class<? extends Module>, Module> entry : modules.entrySet()) {
            if (entry.getValue() instanceof Changeable
                    && ((Changeable) entry.getValue()).isChanged()) {
                Changeable obj = (Changeable) entry.getValue();
                if (!obj.isChanged()) return;
                obj.setChanged(false);
                entry.getValue().flushToDB();
            }
        }
    }

}
