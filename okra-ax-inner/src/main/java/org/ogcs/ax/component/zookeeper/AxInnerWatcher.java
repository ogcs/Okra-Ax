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
    private ZooKeeper zk;
    private Stat stat;
    private long local;

    public AxInnerWatcher(String rootPath, ZooKeeper zk, Stat stat, long local) {
        this.zk = zk;
        this.stat = stat;
        this.rootPath = rootPath;
        this.local = local;
    }

    @Override
    public void process(WatchedEvent event) {
        boolean monitor = true;
        switch (event.getType()) {
            case None:
                monitor = false;
                break;
            case NodeCreated:
                break;
            case NodeDeleted: {
                monitor = false;
                String path = event.getPath();
                if (path != null) {
                    int lastIndex = path.lastIndexOf("/");
                    String module = path.substring(rootPath.length(), lastIndex);
                    String id = path.substring(lastIndex, path.length());
                    components.removeByModule(module, id);
                    System.out.println();
                }
                break;
            }
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
        }
        if (monitor) {
            try {
                if (zk.exists(event.getPath(), false) != null) {
                    monitor(zk, event.getPath(), 0);
                }
            } catch (KeeperException | InterruptedException e) {
                LOG.error("Monitor ZooKeeper : ", e);
            }
        }
    }

    public void monitor(ZooKeeper zk, String root, int deep) {
        try {
            deep -= 1;
            boolean isMonitorNextFloor = deep > 0;
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
                        AxCoInfo axCoInfo = JSON.parseObject(new String(data), AxCoInfo.class);
                        int lastIndex = realPath.lastIndexOf("/");
                        String module = realPath.substring(rootPath.length(), lastIndex);
                        components.add(module, local, axCoInfo);
                        System.out.println();
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
