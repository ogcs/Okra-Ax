/*
 *         Copyright 2016 - 2026 TinyZ.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.okraAx.internal.inner;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.ogcs.app.Session;
import org.okraAx.internal.bean.AxCoInfo;
import org.okraAx.internal.bean.AxShard;
import org.okraAx.internal.zookeeper.ZkConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.zookeeper.Watcher.Event.EventType.*;

/**
 * 内部组件管理器
 * 用于管理连接远程节点客户端列表
 *
 * {@link AxShard}实现最终一致性Hash算法
 *
 * @since 1.0
 */
public class ComponentManager {

    private static final Logger LOG = LogManager.getLogger(ComponentManager.class);
    // id -
    private Map<String, AxInnerClient> clients = new ConcurrentHashMap<>();

    // module
    private Map<String, AxShard<AxInnerClient>> remotes = new ConcurrentHashMap<>();


    //  Local Component Info
    private AxCoInfo local;
    //  ZooKeeper
    private ZooKeeper zookeeper;
    private Stat stat = new Stat();
    private final ZkConfig zkConfig;

    public ComponentManager(AxCoInfo local, ZkConfig zkConfig) {
        this.local = local;
        this.zkConfig = zkConfig;
    }


    public void initialize() {

    }



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
        clients.remove(id);
        AxShard<AxInnerClient> axCoShard = remotes.get(module);
        if (axCoShard != null) {
            AxInnerClient remove = axCoShard.remove(id);
            // Close channel
            if (remove != null) {
                remove.setAutoConnect(false);   //  取消断线重连
                Session session = remove.session();
                if (session != null && session.isOnline())
                    session.close();
            }
            return remove;
        }
        return null;
    }

    //  init zookeeper

    public void initZooKeeper() {
        try {
            zookeeper = new ZooKeeper(zkConfig.getUrl(), zkConfig.getTimeout(), null);
            zookeeper.register(new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    String path = event.getPath();
                    if (path == null && event.getType() == None) {
                        return;
                    }
                    switch (event.getType()) {
                        case None:
                            break;
                        case NodeDeleted: {
                            if (path != null) {
                                int lastIndex = path.lastIndexOf("/");
                                String module = path.substring(path.length() + 1, lastIndex);
                                String id = path.substring(lastIndex + 1, path.length());
                                ComponentManager.this.removeByModule(module, id);
                            }
                            break;
                        }
                        case NodeDataChanged: {
                            monitor(path, true);
                            break;
                        }
                        case NodeCreated:
                        case NodeChildrenChanged: {
                            monitor(path, false);
                            break;
                        }
                    }
                }
            });
            ArrayList<ACL> acls = ZooDefs.Ids.OPEN_ACL_UNSAFE;
            // 1. 监听节点
            for (String path : zkConfig.getWatches()) {
                create(zkConfig.getRoot(), path, new byte[0], acls, CreateMode.PERSISTENT);
            }
            // 2. 创建自身节点
            String localPath = zkConfig.getRoot() + "/" + local;
            create(zkConfig.getRoot(), localPath, JSON.toJSONBytes(local), acls, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            LOG.error("AxZookeeper initialize failed.", e);
        }
    }

    public void create(String root, String path, byte[] data, List<ACL> acls, CreateMode createMode) {
        try {
            String tmpPath = root;
            String[] split = path.split("/");
            for (int i = 0; i < split.length; i++) {
                tmpPath += split[i];
                if (i < split.length - 1) {
                    if (zookeeper.exists(tmpPath, true) == null) {
                        zookeeper.create(tmpPath, null, acls, CreateMode.PERSISTENT);
                    }
                } else {
                    if (zookeeper.exists(tmpPath, true) == null) {
                        zookeeper.create(tmpPath, data, acls, createMode);
                    }
                    monitor(tmpPath, false);
                }
            }
        } catch (InterruptedException | KeeperException e) {
            LOG.error("create zookeeper node failed. ", e);
        }
    }

    public void monitor(String root, boolean cover) {
        monitorDataChanged(root, cover);
        monitorChildrenChanged(root);
    }

    public void monitorDataChanged(String path, boolean cover) {
        try {
            byte[] data = zookeeper.getData(path, true, stat);
            if (data != null && data.length > 0) {
                try {
                    int lastIndex = path.lastIndexOf("/");
                    String id = path.substring(lastIndex + 1, path.length());
                    String module = path.substring(zkConfig.getRoot().length() + 1, lastIndex);
                    if (String.valueOf(local).equals(id)) {
                        return;
                    }
                    if (this.isExist(id)) {
                        if (cover) {
                            this.removeByModule(module, id);
                        } else {
                            return;
                        }
                    }
                    //  Json
//                    System.out.println("DataChanged : " + path + ":" + cover + " => " + new String(data));
                    AxCoInfo axCoInfo = JSON.parseObject(new String(data), AxCoInfo.class);
                    this.add(module, local.getId(), axCoInfo);
                } catch (Exception e) {
                    LOG.error("JSON error : " + new String(data)); // , e
                }
            }
        } catch (Exception e) {
            LOG.error("DataChanged Error. Path : " + path, e);
        }
    }

    public void monitorChildrenChanged(String path) {
        try {
            List<String> children = zookeeper.getChildren(path, true, stat);
            for (String node : children) {
                monitor(path + "/" + node, false);
            }
        } catch (KeeperException | InterruptedException e) {
            LOG.error("ChildrenChanged Error. Path : " + path, e);
        }
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
