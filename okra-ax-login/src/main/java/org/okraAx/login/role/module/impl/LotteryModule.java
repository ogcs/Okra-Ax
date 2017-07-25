package org.okraAx.login.role.module.impl;

import org.okraAx.login.bean.VoItem;
import org.okraAx.login.bean.VoLottery;
import org.okraAx.login.role.Modules;
import org.okraAx.login.role.module.ChangeableModule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 彩票模块
 *
 * @author TinyZ.
 * @version 2017.07.19
 */
public final class LotteryModule extends ChangeableModule {

    private final Map<Integer /* lottery id */, VoLottery> map = new ConcurrentHashMap<>();

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
        for (Map.Entry<Integer, VoLottery> entry : map.entrySet()) {
            if (!entry.getValue().isChanged())
                return;
            entry.getValue().setChanged(false);
            //  TODO: write and flush data to datasource.

        }
    }

    @Override
    public void clear() {

    }


}
