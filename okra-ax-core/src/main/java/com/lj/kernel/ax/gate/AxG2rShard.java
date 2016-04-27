package com.lj.kernel.ax.gate;

import org.ogcs.utilities.math.murmur.Murmur2;

import java.util.*;

public class AxG2rShard {

    public static final int VISUAL_NODE_COUNT = 10;

    private String module;
    private TreeMap<Long, G2RClient> nodes = new TreeMap<>();
    private Map<String, G2RClient> shards = new HashMap<>();
    private Map<Long, G2RClient> cache = new HashMap<>();

    public AxG2rShard(String module) {
        this.module = module;
    }

    public void add(G2RClient shard) {
        shards.put(shard.id(), shard);
        for (int n = 0; n < VISUAL_NODE_COUNT; n++) {
            nodes.put(Murmur2.hash64(shard.id() + "-" + n), shard);
        }
    }

    private void addAll(List<G2RClient> list) {
        for (G2RClient shard : list) {
            shards.put(shard.id(), shard);
            for (int n = 0; n < VISUAL_NODE_COUNT; n++) { // 虚拟节点数量
                nodes.put(Murmur2.hash64(shard.id() + "-" + n), shard);
            }
        }
    }

    public G2RClient remove(String id) {
        G2RClient shard = shards.remove(id);
        if (shard != null) {
            for (int n = 0; n < VISUAL_NODE_COUNT; n++) { // 虚拟节点数量
                long hashCode = Murmur2.hash64(shard.id() + "-" + n);
                nodes.remove(hashCode);
                cache.remove(hashCode);
            }
        }
        return shard;
    }

    public G2RClient getShard(String key) {
        long hash = Murmur2.hash64(key);
        if (cache.containsKey(hash)) {
            return cache.get(hash);
        }
        SortedMap<Long, G2RClient> tail = nodes.tailMap(hash);
        G2RClient t = tail.size() == 0 ? nodes.get(nodes.firstKey()) : tail.get(tail.firstKey());
        cache.put(hash, t);
        return t;
    }
}
