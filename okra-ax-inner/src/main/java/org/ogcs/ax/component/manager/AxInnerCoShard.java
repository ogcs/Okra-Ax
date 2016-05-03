/*
 *   Copyright 2016 - 2026 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.ogcs.ax.component.manager;

import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.utilities.math.murmur.Murmur2;

import java.util.*;

public class AxInnerCoShard {

    public static final int VISUAL_NODE_COUNT = 10;

    private String module;
    private TreeMap<Long, AxInnerClient> nodes = new TreeMap<>();
    private Map<String, AxInnerClient> shards = new HashMap<>();
    private Map<Long, AxInnerClient> cache = new HashMap<>();

    public AxInnerCoShard(String module) {
        this.module = module;
    }

    public void add(AxInnerClient shard) {
        shards.put(shard.id(), shard);
        for (int n = 0; n < VISUAL_NODE_COUNT; n++) {
            nodes.put(Murmur2.hash64(shard.id() + "-" + n), shard);
        }
    }

    private void addAll(List<AxInnerClient> list) {
        for (AxInnerClient shard : list) {
            shards.put(shard.id(), shard);
            for (int n = 0; n < VISUAL_NODE_COUNT; n++) { // 虚拟节点数量
                nodes.put(Murmur2.hash64(shard.id() + "-" + n), shard);
            }
        }
    }

    public AxInnerClient remove(String id) {
        AxInnerClient shard = shards.remove(id);
        if (shard != null) {
            for (int n = 0; n < VISUAL_NODE_COUNT; n++) { // 虚拟节点数量
                long hashCode = Murmur2.hash64(shard.id() + "-" + n);
                nodes.remove(hashCode);
                cache.remove(hashCode);
            }
        }
        return shard;
    }

    public AxInnerClient getShard(String key) {
        long hash = Murmur2.hash64(key);
        if (cache.containsKey(hash)) {
            return cache.get(hash);
        }
        SortedMap<Long, AxInnerClient> tail = nodes.tailMap(hash);
        AxInnerClient t = tail.size() == 0 ? nodes.get(nodes.firstKey()) : tail.get(tail.firstKey());
        cache.put(hash, t);
        return t;
    }
}
