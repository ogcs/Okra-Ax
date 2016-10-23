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

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import org.ogcs.ax.component.exception.RegisteredException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Ax Console .
 * <p>
 * Register and interpret command.
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
@Service("AxConsoleManager")
public class AxConsoleManager {

    private final HashMap<Integer, DescriptorProto> descriptors = new HashMap<>();
    private final Set<Integer> authorized = new HashSet<>();

    /**
     * Get the command instance.
     */
    public DescriptorProto interpret(int cmd) throws Exception {
        if (descriptors.containsKey(cmd)) {
            return descriptors.get(cmd);
        } else {
            throw new Exception("Unknown command : " + cmd);
        }
    }

    public boolean isAuthorized(int cmd) {
        return authorized.contains(cmd);
    }

    public void register(int id, DescriptorProto descriptor, boolean isAuthorized) throws Exception {
        if (descriptors.containsKey(id)) {
            throw new RegisteredException("The command code [ " + id + " ] is registered.");
        }
        if (isAuthorized)
            authorized.add(id);
        descriptors.put(id, descriptor);
    }

    /**
     * Get the installed commands
     */
    public Map<Integer, DescriptorProto> getCommandMap() {
        return descriptors;
    }
}
