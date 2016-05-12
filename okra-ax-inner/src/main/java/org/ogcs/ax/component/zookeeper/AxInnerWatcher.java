package org.ogcs.ax.component.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.ogcs.app.AppContext;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.SpringContext;
import org.ogcs.ax.component.manager.AxInnerCoManager;

import java.util.List;

/**
 * ZooKeeper管理集群
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/11
 * @since 1.0
 */
public class AxInnerWatcher implements Watcher {

    private static final Logger LOG = LogManager.getLogger(AxInnerWatcher.class);

    private AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
    private final String rootPath;
    private final ZooKeeper zk;
    private final Stat stat;
    private final long local;

    public AxInnerWatcher(String rootPath, ZooKeeper zk, Stat stat, long local) {
        this.zk = zk;
        this.stat = stat;
        this.rootPath = rootPath;
        this.local = local;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.toString());
        String path = event.getPath();
        if (path == null && event.getType() == Event.EventType.None) {
            LOG.info("WatchEvent : None");
            return;
        }
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted: {
                if (path != null) {
                    int lastIndex = path.lastIndexOf("/");
                    String module = path.substring(rootPath.length() + 1, lastIndex);
                    String id = path.substring(lastIndex + 1, path.length());
                    components.removeByModule(module, id);
                    System.out.println();
                }
                break;
            }
            case NodeDataChanged: {
                monitorDataChanged(path, local, true);
                break;
            }
            case NodeChildrenChanged: {
                monitorChildrenChanged(path, local);
                break;
            }
        }
        System.out.println(components.toString());
    }

    public void monitorDataChanged(String path, long local, boolean cover) {
        try {
            byte[] data = zk.getData(path, true, stat);
            if (data != null && data.length > 0) {
                System.out.println("Watch : " + path + " => " + new String(data));
                try {
                    int lastIndex = path.lastIndexOf("/");
                    String id = path.substring(lastIndex + 1, path.length());
                    String module = path.substring(rootPath.length() + 1, lastIndex);
                    if (components.isExist(id)) {
                        if (cover) {
                            components.removeByModule(module, id);
                        } else {
                            return;
                        }
                    }
                    //  Json
                    AxCoInfo axCoInfo = JSON.parseObject(new String(data), AxCoInfo.class);
                    components.add(module, local, axCoInfo);
                } catch (Exception e) {
                    LOG.error("JSON error : "); // , e
                }
            }
        } catch (Exception e) {
            LOG.error("Error from function getData() and path : " + path, e);
        }
    }

    public void monitorChildrenChanged(String path, long local) {
        try {
            List<String> children = zk.getChildren(path, true, stat);
            for (String node : children) {
                String realPath = path + "/" + node;
                monitorDataChanged(realPath, local, false);
            }
        } catch (KeeperException | InterruptedException e) {
            LOG.error("Error from function getChildren() and path : " + path, e);
        }
    }

    public void monitor(ZooKeeper zk, String root, int deep) {
        try {
            boolean isMonitorNextFloor = deep > 0;
            deep -= 1;
            // 1. monitor EventType.NodeChildrenChanged
            List<String> children = zk.getChildren(root, true, stat);
            for (String nodePath : children) {
                // 2. monitor EventType.NodeDataChanged
                String realPath = root + "/" + nodePath;
                try {
                    byte[] data = zk.getData(realPath, true, stat);
                    if (data != null && data.length > 0) {
                        System.out.println("Node : " + realPath + " => " + new String(data));
                        //  Json
                        try {
                            AxCoInfo axCoInfo = JSON.parseObject(new String(data), AxCoInfo.class);
                            int lastIndex = realPath.lastIndexOf("/");
                            String module = realPath.substring(rootPath.length() + 1, lastIndex);
                            components.add(module, local, axCoInfo);
                            System.out.println();
                        } catch (Exception e) {
                            LOG.error("JSON error : ", e);
                        }
                    }
                } catch (Exception e) {
                    LOG.error("Error from function getData() and path : " + root, e);
                }
                if (isMonitorNextFloor) {
                    monitor(zk, realPath, deep);
                }
            }
        } catch (KeeperException | InterruptedException e) {
            LOG.error("Error from function getChildren() and path : " + root, e);
        }
    }
}
