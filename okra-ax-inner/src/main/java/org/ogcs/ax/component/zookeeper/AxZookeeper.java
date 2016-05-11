package org.ogcs.ax.component.zookeeper;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.AxProperties;

import java.io.IOException;

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
    private String root;
    private String module;
    private long local;
    private AxCoInfo info;

    /**
     *
     * @param connectString ZooKeeper连接字符串
     * @param timeout   ZooKeeper超时设置
     * @param root  根节点
     * @param module    本地组件模块
     * @param local 本地组件的ID
     * @param info  本地组件信息
     */
    public AxZookeeper(String connectString, int timeout, String root, String module, long local, AxCoInfo info) {
        this.connectString = connectString;
        this.timeout = timeout;
        this.root = root;
        this.module = module;
        this.local = local;
        this.info = info;
    }

    public void init() {
        try {
            AxInnerWatcher watcher = new AxInnerWatcher(root, zk, stat, local);
            zk = new ZooKeeper(connectString, timeout, watcher);

//        zk.create("/ax/remote", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/ax/gate", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/ax/monitor", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/ax/remote/chess", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            // 监听节点
            for (String watch : AxProperties.axZkWatches) {
                String[] split = watch.split("/");
                String nodePath = AxProperties.axZkRootPath;
                for (int i = 0; i < split.length; i++) {
                    nodePath += "/" + split[i];
                    if (i < split.length - 1) { // 根节点的父节点不存在时, 创建父节点
                        if (zk.exists(nodePath, true) == null) {
                            zk.create(nodePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        }
                    }
                    watcher.monitor(zk, nodePath, 1);
                }
            }
            // 创建自身节点
            String localPath = root + "/" + module + "/" + local;
            zk.create(localPath, JSONObject.toJSONBytes(info), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            // 监控全部节点
            watcher.monitor(zk, root, 100);
        } catch (InterruptedException | KeeperException | IOException e) {
            e.printStackTrace();
        }
    }
}
