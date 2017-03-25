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
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.ogcs.app.AppContext;
import org.okraAx.internal.bean.AxCoInfo;
import org.okraAx.internal.component.AxInnerCoManager;
import org.okraAx.internal.SpringContext;

import java.util.List;

/**
 * ZooKeeper Watcher
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class ZkWatcher implements Watcher {

    private static final Logger LOG = LogManager.getLogger(ZkWatcher.class);

    private AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
    private final String rootPath;
    private final ZooKeeper zk;
    private final Stat stat;
    private final long local;

    public ZkWatcher(String rootPath, ZooKeeper zk, Stat stat, long local) {
        this.zk = zk;
        this.stat = stat;
        this.rootPath = rootPath;
        this.local = local;
    }

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (path == null && event.getType() == EventType.None) {
            return;
        }
        switch (event.getType()) {
            case None:
                break;
            case NodeDeleted: {
                if (path != null) {
                    int lastIndex = path.lastIndexOf("/");
                    String module = path.substring(rootPath.length() + 1, lastIndex);
                    String id = path.substring(lastIndex + 1, path.length());
                    components.removeByModule(module, id);
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

    public void monitor(String root, boolean cover) {
        monitorDataChanged(root, cover);
        monitorChildrenChanged(root);
    }

    public void monitorDataChanged(String path, boolean cover) {
        try {
            byte[] data = zk.getData(path, true, stat);
            if (data != null && data.length > 0) {
                try {
                    int lastIndex = path.lastIndexOf("/");
                    String id = path.substring(lastIndex + 1, path.length());
                    String module = path.substring(rootPath.length() + 1, lastIndex);
                    if (String.valueOf(local).equals(id)) {
                        return;
                    }
                    if (components.isExist(id)) {
                        if (cover) {
                            components.removeByModule(module, id);
                        } else {
                            return;
                        }
                    }
                    //  Json
//                    System.out.println("DataChanged : " + path + ":" + cover + " => " + new String(data));
                    AxCoInfo axCoInfo = JSON.parseObject(new String(data), AxCoInfo.class);
                    components.add(module, local, axCoInfo);
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
            List<String> children = zk.getChildren(path, true, stat);
            for (String node : children) {
                monitor(path + "/" + node, false);
            }
        } catch (KeeperException | InterruptedException e) {
            LOG.error("ChildrenChanged Error. Path : " + path, e);
        }
    }
}
