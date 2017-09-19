package org.okraAx.login.role.module.impl;

import org.okraAx.login.bean.VoItem;
import org.okraAx.login.role.Modules;
import org.okraAx.login.role.module.AbstractModule;
import org.okraAx.login.role.module.DataSourceModule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 道具模块
 *
 * @author TinyZ.
 * @version 2017.05.18
 */
public final class ItemModule extends DataSourceModule<VoItem, Long> {

    private final Map<Long /* itemId */, VoItem> items = new ConcurrentHashMap<>();
//    private final Map<Integer /* cfgItemId */, List<VoItem>> cfgItemMap = new ConcurrentHashMap<>();

    public ItemModule(Modules modules) {
        super(modules);
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public void loadFromDB() {

    }

    @Override
    public void batchInsert(Collection<VoItem> bean) {

    }

    @Override
    public void batchUpdate(Collection<VoItem> bean) {

    }

    @Override
    public void batchDelete(Collection<VoItem> bean) {

    }

    @Override
    public void clear() {

    }

    public VoItem getByItemId(long itemId) {
        return items.get(itemId);
    }

    public List<VoItem> selectByCfgItemId(int cfgItemId, boolean onlyForever) {
        List<VoItem> list = new ArrayList<>();
        for (Map.Entry<Long, VoItem> entry : items.entrySet()) {
            if (isItemExpired(entry.getValue())) continue;
            if (entry.getValue().getCfgItemId() != cfgItemId) continue;
            if (onlyForever) {
                if (entry.getValue().getExpire() > 0) continue;
            }
            list.add(entry.getValue());
        }
        //  即将过期的优先使用
        if (!onlyForever) {
            Collections.sort(list, new Comparator<VoItem>() {
                @Override
                public int compare(VoItem o1, VoItem o2) {
                    return o1.getExpire() - o2.getExpire();
                }
            });
        }
        return list;
    }

    //  TODO: itemId有程序生成 - 不需要数据库自增
    public void addItem(int cfgItemId, int amount, long expire) {

    }

    public void addItem(VoItem voItem) {
        //  TODO: itemId有程序生成 - 不需要数据库自增
        if (items.containsKey(voItem.getItemId())) {
            VoItem voItem1 = items.get(voItem.getItemId());
            voItem1.setAmount(voItem1.getAmount() + voItem.getAmount());
            putUpdate(voItem);
        } else {
            items.put(voItem.getItemId(), voItem);
            putInsert(voItem);
        }
    }

    public boolean useItem(int cfgItemId, int amount, boolean onlyForever) {
        List<VoItem> list = selectByCfgItemId(cfgItemId, onlyForever);
        if (list.size() == amount) {
            for (VoItem voItem : list) {
                putDelete(voItem);
                items.remove(voItem.getItemId());
            }
            return true;
        } else if (list.size() > amount) {
            int usedAmount = 0;
            for (VoItem voItem : list) {
                if (usedAmount + voItem.getAmount() > amount) {
                    voItem.setAmount(voItem.getAmount() - amount + usedAmount);
                    putUpdate(voItem);
                } else {
                    usedAmount += voItem.getAmount();
                    putDelete(voItem);
                    items.remove(voItem.getItemId());
                }
                if (usedAmount >= amount) {
                    break;
                }
            }
            return true;
        } else
            return false;
    }


    private void deleteItem() {

    }

    /**
     * 道具是否过期
     */
    private boolean isItemExpired(VoItem voItem) {
        return voItem != null && !(voItem.getExpire() > 0 && voItem.getExpire() < System.currentTimeMillis());
    }


}
