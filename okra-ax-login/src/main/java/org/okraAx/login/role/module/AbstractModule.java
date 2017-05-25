/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.okraAx.login.role.module;


import org.okraAx.login.role.Modules;

/**
 * 抽象模块.
 *
 * @author TinyZ
 * @version 2017.05.18.
 */
public abstract class AbstractModule implements Module {

    protected final Modules modules;
    private volatile boolean initialized = false;

    public AbstractModule(Modules modules) {
        this.modules = modules;
    }

    /**
     * 初始化数据
     */
    @Override
    public void init() {
        if (initialized)
            return;
        load();
        this.initialized = true;
    }

    @Override
    public void load() {
        loadFromDB();
    }

    @Override
    public void dispose() {
        clear();
        initialized = false;
    }

    public abstract void clear();
}
