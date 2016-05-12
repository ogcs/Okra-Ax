/*
 *   Copyright 2016 - 2026 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.ogcs.ax.component.manager;

import org.ogcs.app.Session;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内部组件管理器
 * 用于管理连接远程节点客户端列表
 *
 * {@link AxShard}实现最终一致性Hash算法
 *
 * @since 1.0
 */
@Service("axCoManager")
public class AxInnerCoManager {

    // id -
    private Map<String, AxInnerClient> clients = new ConcurrentHashMap<>();

    // module
    private Map<String, AxShard<AxInnerClient>> remotes = new ConcurrentHashMap<>();

    public boolean isExist(String id) {
        return clients.containsKey(id);
    }

    public void add(String module, long local, AxCoInfo info) {
        AxInnerClient client = new AxInnerClient(module, local, info);
        client.start(); //  Connect to component
        add(module, client);
    }

    public void add(String module, AxInnerClient client) {
        AxShard<AxInnerClient> axCoShard = remotes.get(module);
        if (axCoShard == null) {
            axCoShard = new AxShard<>(module);
            remotes.put(module, axCoShard);
            clients.put(client.id(), client);
        }
        axCoShard.add(client);
    }

    public AxInnerClient get(String id) {
        return clients.get(id);
    }

    public AxShard getAxCoShard(String module) {
        return remotes.get(module);
    }

    public AxInnerClient getByHash(int module, String key) {
        return getByHash(String.valueOf(module), key);
    }

    public AxInnerClient getByHash(String module, String key) {
        AxShard<AxInnerClient> axCoShard = remotes.get(module);
        if (axCoShard != null) {
            return axCoShard.getShard(key);
        }
        return null;
    }

    public AxInnerClient removeByModule(String module, String id) {
        System.out.println("REMOVE - 移除组件");
        clients.remove(id);
        AxShard<AxInnerClient> axCoShard = remotes.get(module);
        if (axCoShard != null) {
            AxInnerClient remove = axCoShard.remove(id);
            // Close channel
            if (remove != null) {
                remove.setAutoConnect(false);   //  取消断线重连
                Session session = remove.session();
                if (session != null && session.isOnline())
                    session.ctx().close();
            }
            return remove;
        }
        return null;
    }

    @Override
    public String toString() {
        String var = "";
        var += "=>Client size : " + clients.size() + ", ";
        for (Map.Entry<String, AxShard<AxInnerClient>> entry : remotes.entrySet()) {
            var += "\n=>Module:" + entry.getKey() + ", " + entry.getValue().toString();
        }
        return var;
    }
}
