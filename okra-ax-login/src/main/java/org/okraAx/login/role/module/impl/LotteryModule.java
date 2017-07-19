package org.okraAx.login.role.module.impl;

import org.okraAx.login.bean.VoItem;
import org.okraAx.login.role.Modules;
import org.okraAx.login.role.module.AbstractModule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 彩票模块
 *
 * @author TinyZ.
 * @version 2017.07.19
 */
public final class LotteryModule extends AbstractModule {

    private final Map<Long /* itemId */, VoItem> items = new ConcurrentHashMap<>();
//    private final Map<Integer /* cfgItemId */, List<VoItem>> cfgItemMap = new ConcurrentHashMap<>();

    //  插入列表
    private Map<Long, VoItem> insertMap = new ConcurrentHashMap<>();
    //  更新列表
    private Map<Long, VoItem> updateMap = new ConcurrentHashMap<>();
    //  删除列表
    private Map<Long, VoItem> deleteMap = new ConcurrentHashMap<>();


    public LotteryModule(Modules modules) {
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
    public void flushToDB() {
        synchronized (this) {
            for (Map.Entry<Long, VoItem> entry : insertMap.entrySet()) {
                if (deleteMap.containsKey(entry.getKey())) continue;    //  已经被使用完
                if (updateMap.containsKey(entry.getKey())) {
                    updateMap.remove(entry.getKey());
                }
                //  insert
            }
            for (Map.Entry<Long, VoItem> entry : updateMap.entrySet()) {
                if (deleteMap.containsKey(entry.getKey())) continue;    //  已经被使用完
                //  update
            }
            for (Map.Entry<Long, VoItem> entry : deleteMap.entrySet()) {
                //  delete
            }
            insertMap.clear();
            updateMap.clear();
            deleteMap.clear();
        }
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
            updateMap.put(voItem.getItemId(), voItem);
        } else {
            items.put(voItem.getItemId(), voItem);
            insertMap.put(voItem.getItemId(), voItem);
        }
    }

    public boolean useItem(int cfgItemId, int amount, boolean onlyForever) {
        List<VoItem> list = selectByCfgItemId(cfgItemId, onlyForever);
        if (list.size() == amount) {
            for (VoItem voItem : list) {
                deleteMap.put(voItem.getItemId(), voItem);
                if (updateMap.containsKey(voItem.getItemId()))
                    updateMap.remove(voItem.getItemId());
                items.remove(voItem.getItemId());
            }
            return true;
        } else if (list.size() > amount) {
            int usedAmount = 0;
            for (VoItem voItem : list) {
                if (usedAmount + voItem.getAmount() > amount) {
                    voItem.setAmount(voItem.getAmount() - amount + usedAmount);
                    updateMap.put(voItem.getItemId(), voItem);
                } else {
                    usedAmount += voItem.getAmount();
                    deleteMap.put(voItem.getItemId(), voItem);
                    if (updateMap.containsKey(voItem.getItemId()))
                        updateMap.remove(voItem.getItemId());
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
