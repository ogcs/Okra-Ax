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

package org.okraAx.internal.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.okraAx.internal.bean.AxCoInfo;

import java.util.List;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class AxZookeeper {

    private static final Logger LOG = LogManager.getLogger(AxZookeeper.class);
    private ZooKeeper zk;
    private static Stat stat = new Stat();

    private String connectString;
    private int timeout;
    private String[] watches;

    private String root;
    private String module;
    private long local;
    private AxCoInfo info;

    /**
     * @param connectString ZooKeeper连接字符串
     * @param timeout       ZooKeeper超时设置
     * @param watches       监听的节点列表
     * @param root          根节点
     * @param module        本地组件模块
     * @param local         本地组件的ID
     * @param info          本地组件信息
     */
    public AxZookeeper(String connectString, int timeout, String[] watches, String root, String module, long local, AxCoInfo info) {
        this.connectString = connectString;
        this.timeout = timeout;
        this.watches = watches;
        this.root = root;
        this.module = module;
        this.local = local;
        this.info = info;
    }

    public void init() {
        try {
            zk = new ZooKeeper(connectString, timeout, null);
            AxInnerWatcher watcher = new AxInnerWatcher(root, this.zk, stat, local);
            zk.register(watcher);
            // 1. 监听节点
            for (String watch : watches) {
                create(root, watch, watcher, "".getBytes(), zk, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            // 2. 创建自身节点
            String localPath = module + "/" + local;
            create(root, localPath, watcher, JSON.toJSONBytes(info), zk, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            LOG.error("AxZookeeper initialize failed.", e);
        }
    }

    public void create(String root, String path, AxInnerWatcher watcher, byte[] data, ZooKeeper zk, List<ACL> acl, CreateMode createMode) {
        try {
            String[] split = path.split("/");
            if (split.length > 0) {
                String nodePath = root;
                for (int i = 0; i < split.length; i++) {
                    nodePath += "/" + split[i];
//                    System.out.println(nodePath);
                    if (i < split.length - 1) {
                        if (zk.exists(nodePath, true) == null) {
                            zk.create(nodePath, null, acl, CreateMode.PERSISTENT); // 父节点没有数据
                        }
                        // watcher.monitor(nodePath, false);   // 不监控父节点 - 有需求在监控列表watches中设置
                    } else {
                        if (zk.exists(nodePath, true) == null) {
                            zk.create(nodePath, data, acl, createMode);
                        }
                        watcher.monitor(nodePath, false);
                    }
                }
            }
        } catch (InterruptedException | KeeperException e) {
            LOG.error("create zookeeper node failed. ", e);
        }
    }

}
