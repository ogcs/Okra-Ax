/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ogcs.utilities.math;


/**
 * The Weighted Round-Robin Scheduling Algorithm
 * <br/>
 * <br/>Supposing that there is a server set S = {S0, S1,.., Sn-1};
 * <br/>W(Si) indicates the weight of Si;
 * <br/>i indicates the server selected last time, and i is initialized with -1;
 * <br/>cw is the current weight in scheduling, and cw is initialized with zero;
 * <br/>max(S) is the maximum weight of all the servers in S;
 * <br/>gcd(S) is the greatest common divisor of all server weights in S;
 * <br/>
 * For example, the real servers, A, B and C, have the weights, 4, 3, 2 respectively, a scheduling sequence will be AABABCABC in a scheduling period
 * (mod sum(Wi)).
 *
 * @author : TinyZ.
 * @version 2017.10.10
 * @see <a href="http://kb.linuxvirtualserver.org/wiki/Weighted_Round-Robin_Scheduling">LVS-WRR Algorithm</a>
 */
public final class WeightRoundRobinAlgorithm<T> {

    private final T[] items;
    private final int[] weights;
    private final int n;
    private final int gcd;
    private final int max;

    private volatile int i = -1;
    private volatile int cw = 0;

    public WeightRoundRobinAlgorithm(final T[] items, final int[] weights) {
        if (items == null)
            throw new NullPointerException("items");
        if (weights == null)
            throw new NullPointerException("weights");
        if (weights.length <= 0 || weights.length != items.length)
            throw new IllegalArgumentException("weights's length is zero or not equals items's length");
        this.items = items;
        this.weights = weights;
        n = items.length;
        gcd = MathUtil.gcd(weights);
        //  lookup the max weight
        int temp = 0;
        for (int weight : weights) {
            if (weight > temp)
                temp = weight;
        }
        max = temp;
    }

    /**
     * @return Lookup the next item and return it.
     */
    public synchronized T next() {
        while (true) {
            i = (i + 1) % n;
            if (i == 0) {
                cw = cw - gcd;
                if (cw <= 0) {
                    cw = max;
                    if (cw == 0) {
                        return null;
                    }
                }
            }
            if (weights[i] >= cw)
                return items[i];
        }
    }
}
