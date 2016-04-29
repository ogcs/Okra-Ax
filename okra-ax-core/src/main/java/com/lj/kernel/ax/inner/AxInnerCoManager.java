package com.lj.kernel.ax.inner;

import com.lj.kernel.ax.SpringContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/28
 */
@Service(SpringContext.MANAGER_AX_COMPONENT)
public class AxInnerCoManager {

    // id -
    private Map<String, AxInnerClient> clients = new ConcurrentHashMap<>();

    // module
    private Map<String, AxInnerCoShard> remotes = new ConcurrentHashMap<>();

    public void add(String module, long id, long local, String host, int port) {
        AxInnerClient client = new AxInnerClient(module, id, local, host, port);
        client.start();
        add(module, client);
    }

    public void add(String module, AxInnerClient client) {
        AxInnerCoShard axCoShard = remotes.get(module);
        if (axCoShard == null) {
            axCoShard = new AxInnerCoShard(module);
            remotes.put(module, axCoShard);
            clients.put(client.id(), client);
        }
        axCoShard.add(client);
    }

    public AxInnerClient get(String id) {
        return clients.get(id);
    }

    public AxInnerCoShard getAxCoShard(String module) {
        return remotes.get(module);
    }

    public AxInnerClient getByHash(int module, String key) {
        return getByHash(String.valueOf(module), key);
    }

    public AxInnerClient getByHash(String module, String key) {
        AxInnerCoShard axCoShard = remotes.get(module);
        if (axCoShard != null) {
            return axCoShard.getShard(key);
        }
        return null;
    }

    public AxInnerClient removeByModule(String module, String id) {
        clients.remove(id);
        AxInnerCoShard axCoShard = remotes.get(module);
        if (axCoShard != null) {
            return axCoShard.remove(id);
        }
        return null;
    }
}
