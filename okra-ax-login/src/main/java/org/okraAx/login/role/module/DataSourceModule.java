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
    public void flushChangedData() {
        if (!isChanged())
            return;
        if (insertMap.isEmpty()
                && updateMap.isEmpty()
                && deleteMap.isEmpty())
            return;
        setChanged(false);
        synchronized (this) {
            //
            Iterator<Map.Entry<K, B>> itInsert = insertMap.entrySet().iterator();
            while (itInsert.hasNext()) {
                Map.Entry<K, B> entry = itInsert.next();
                if (deleteMap.containsKey(entry.getKey())) {
                    itInsert.remove();
                    continue;
                }
                if (updateMap.containsKey(entry.getKey())) {
                    updateMap.remove(entry.getKey());
                }
            }
            batchInsert(insertMap.values());
            //
            Iterator<Map.Entry<K, B>> itUpdate = updateMap.entrySet().iterator();
            while (itUpdate.hasNext()) {
                Map.Entry<K, B> entry = itUpdate.next();
                if (deleteMap.containsKey(entry.getKey())) {
                    itUpdate.remove();
                    continue;
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
