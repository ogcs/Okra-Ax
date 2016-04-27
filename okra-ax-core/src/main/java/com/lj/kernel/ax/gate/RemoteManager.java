package com.lj.kernel.ax.gate;

import com.lj.kernel.ax.SpringContext;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 远程节点管理器
 * 在Gate服务器中管理集群内部的所有远程节点
 * @author TinyZ on 2016/4/24.
 */
@Service(SpringContext.MANAGER_REMOTE)
public class RemoteManager {

    private Map<String, AxG2rShard> remotes = new ConcurrentHashMap<>();

    public void add(String gateId, String module, String remoteId, String host, int port) {
        G2RClient client = new G2RClient(gateId, remoteId, host, port) {
            @Override
            public void connectionInactive(ChannelHandlerContext ctx) throws Exception {
                AxG2rShard axG2rShard = remotes.get(module);
                if (axG2rShard != null) {
                    remove(module, gateId);
                }
                super.connectionInactive(ctx);
            }
        };
        client.start();
        add(module, client);
    }

    public void add(String module, G2RClient client) {
        AxG2rShard axG2rShard = remotes.get(module);
        if (axG2rShard == null) {
            axG2rShard = new AxG2rShard(module);
            remotes.put(module, axG2rShard);
        }
        axG2rShard.add(client);
    }

    public G2RClient get(int module, String key) {
        return get(String.valueOf(module), key);
    }

    public G2RClient get(String module, String key) {
        AxG2rShard axG2rShard = remotes.get(module);
        if (axG2rShard != null) {
            return axG2rShard.getShard(key);
        }
        return null;
    }

    public G2RClient remove(String module, String id) {
        AxG2rShard axG2rShard = remotes.get(module);
        if (axG2rShard != null) {
            return axG2rShard.remove(id);
        }
        return null;
    }
}
