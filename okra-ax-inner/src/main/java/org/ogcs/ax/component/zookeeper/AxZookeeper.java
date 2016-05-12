package org.ogcs.ax.component.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.ogcs.ax.component.AxCoInfo;

import java.io.IOException;
import java.util.List;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/11
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
     * @param watches
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

//        zk.create("/ax/remote", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/ax/gate", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/ax/monitor", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/ax/remote/chess", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            // 监听节点
            for (String watch : watches) {
                create(root + "/" + watch, watcher, "".getBytes(), zk, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

//                String[] split = watch.split("/");
//                String nodePath = root;
//                for (int i = 0; i < split.length; i++) {
//                    nodePath += "/" + split[i];
//                    if (i < split.length - 1) { // 根节点的父节点不存在时, 创建父节点
//                        if (zk.exists(nodePath, true) == null) {
//                            zk.create(nodePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//                        }
//                    }
//                    watcher.monitor(zk, nodePath, 1); // TODO: 是否可以移除10层的限制 - 改为监控全部
//                }
            }
            // 创建自身节点
            String localPath = root + "/" + module + "/" + local;
            create(localPath, watcher, JSON.toJSONBytes(info), zk, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//            zk.create(localPath, JSON.toJSONBytes(info), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            // 监控全部节点
            watcher.monitor(zk, root, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(String path, AxInnerWatcher watcher, byte[] data, ZooKeeper zk, List<ACL> acl, CreateMode createMode) {
        try {
            String[] split = path.split("/");
            if (split.length > 0) {
                String nodePath = "";
                for (int i = 0; i < split.length; i++) {
                    nodePath += (i == 0 ? "" : "/") + split[i];
                    if (nodePath.length() <= 0) {
                        continue;
                    }
                    System.out.println(nodePath);
                    if (i < split.length - 1) {
                        if (zk.exists(nodePath, true) == null) {
                            zk.create(nodePath, null, acl, CreateMode.PERSISTENT); // 父节点没有数据
                        }
                        watcher.monitorChildrenChanged(nodePath, local);
                    } else {
                        if (zk.exists(nodePath, true) == null) {
                            zk.create(nodePath, data, acl, createMode);
                        }
                        watcher.monitorDataChanged(nodePath, local, false);
                    }
                }
            }
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

}
