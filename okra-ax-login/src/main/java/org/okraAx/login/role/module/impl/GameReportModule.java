package org.okraAx.login.role.module.impl;

import org.okraAx.login.bean.GameReport;
import org.okraAx.login.role.Modules;
import org.okraAx.login.role.module.AbstractModule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 战报模块
 *
 * @author TinyZ.
 * @version 2017.05.18
 */
public final class GameReportModule extends AbstractModule {

    private final Map<Integer /* game module */, GameReport> reports = new ConcurrentHashMap<>();

    public GameReportModule(Modules modules) {
        super(modules);
    }

    @Override
    public int id() {
        return 0;
    }

    /**
     * 获取游戏报告
     */
    public GameReport getGameReport(int type) {
        return reports.get(type);
    }

    @Override
    public void loadFromDB() {

    }

    @Override
    public void flushToDB() {

    }

    @Override
    public void clear() {

    }
}
