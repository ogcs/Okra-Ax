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

package org.okraAx.internal.old;

import org.okraAx.internal.core.AxCallback;

public class BlockingCallback<T> implements AxCallback<T> {

    private T msg;
    private boolean isDone;

    @Override
    public void run(T msg) {
        this.msg = msg;
        synchronized (this) {
            isDone = true;
            notifyAll();
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public T get() {
        return msg;
    }
}
