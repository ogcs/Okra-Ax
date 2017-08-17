package org.okraAx.login.role.module;

import org.okraAx.internal.core.Changeable;
import org.okraAx.login.bean.ChangeableBean;
import org.okraAx.login.role.Modules;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现简单的数据延迟写入支持.
 *
 * @author TinyZ.
 * @version 2017.07.20
 */
public abstract class DataSourceModule<B extends ChangeableBean<K>, K>
        extends AbstractModule implements Changeable {

    private Map<K, B> insertMap = new ConcurrentHashMap<>();
    private Map<K, B> updateMap = new ConcurrentHashMap<>();
    private Map<K, B> deleteMap = new ConcurrentHashMap<>();

    private volatile boolean isChanged = false;

    public DataSourceModule(Modules modules) {
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

    /**
     * Flush all data to database.
     */
    @Override
    public void flushToDB() {
        if (!isChanged())
            return;
        if (insertMap.isEmpty()
                && updateMap.isEmpty()
                && deleteMap.isEmpty())
            return;
        setChanged(false);
        synchronized (this) {
            //
            for (K key : insertMap.keySet()) {
                if (deleteMap.containsKey(key)) {
                    insertMap.remove(key);
                    continue;
                }
                if (updateMap.containsKey(key)) {
                    updateMap.remove(key);
                }
            }
            batchInsert(insertMap.values());
            //
            for (K key : updateMap.keySet()) {
                if (deleteMap.containsKey(key)) {
                    updateMap.remove(key);
                }
            }
            batchUpdate(updateMap.values());
            //
            batchDelete(deleteMap.values());
            //  clear all cache.
            insertMap.clear();
            updateMap.clear();
            deleteMap.clear();
        }
    }

    public void putInsert(B bean) {
        insertMap.put(bean.beanKey(), bean);
    }

    public abstract void batchInsert(Collection<B> bean);

    public void putUpdate(B bean) {
        insertMap.put(bean.beanKey(), bean);
    }

    public abstract void batchUpdate(Collection<B> bean);

    public void putDelete(B bean) {
        insertMap.put(bean.beanKey(), bean);
    }

    public abstract void batchDelete(Collection<B> bean);

}
