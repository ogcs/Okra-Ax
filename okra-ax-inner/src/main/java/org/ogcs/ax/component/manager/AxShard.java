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

package org.ogcs.ax.component.manager;

import org.ogcs.ax.component.AxComponent;
import org.ogcs.utilities.math.murmur.Murmur2;

import java.util.*;

public class AxShard<C extends AxComponent> {

    public static final int VISUAL_NODE_COUNT = 100;

    private String module;
    private TreeMap<Long, C> nodes;
    private Map<String, C> resources = new HashMap<>();

    public AxShard(String module) {
        this.module = module;
        this.nodes = new TreeMap<>();
    }

    public AxShard(String module, List<C> shards) {
        this.module = module;
        this.nodes = new TreeMap<>();
        addAll(shards);
    }

    public void addAll(List<C> list) {
        for (C shard : list) {
            add(shard);
        }
    }

    public void add(C shard) {
        resources.put(shard.id(), shard);
        for (int n = 0; n < VISUAL_NODE_COUNT; n++) {
            nodes.put(Murmur2.hash64(shard.id() + "-NODE-" + n), shard);
        }
    }

    public C remove(String id) {
        C shard = resources.remove(id);
        if (shard != null) {
            for (int n = 0; n < VISUAL_NODE_COUNT; n++) {
                long hashCode = Murmur2.hash64(shard.id() + "-NODE-" + n);
                nodes.remove(hashCode);
            }
        }
        return shard;
    }

    public C getShard(String key) {
        long hash = Murmur2.hash64(key);
        SortedMap<Long, C> tail = nodes.tailMap(hash);
        return tail.isEmpty() ? nodes.get(nodes.firstKey()) : tail.get(tail.firstKey());
    }

    @Override
    public String toString() {
        String var = "Shard : [";
        for (C c : resources.values()) {
            var += c.id() + ",";
        }
        var += "]";
        return var;
    }
}
