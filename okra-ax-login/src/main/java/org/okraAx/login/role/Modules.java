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

package org.okraAx.login.role;

import org.okraAx.login.role.module.Module;

/**
 * Game Role' Module Interface.
 * The role instance comprise by many module and player's base information.
 * These modules manage role's optional data or information.
 * <pre>
 *     Example :
 *     Role -   Player's Base Information.
 *          |-  ItemModule
 *          |-  PVEModule
 *          |-  xxxModule
 *      note 1. ItemModule : Player have many items in some game, some don't.
 *      The ItemModule make easy to manage player's items
 * </pre>
 *
 * @author TinyZ
 * @version 2017.01.13.
 * @since 1.0
 */
public interface Modules {

    /**
     * Lazy load and init the module.
     */
    void lazyLoad();

    /**
     * Register role module.
     *
     * @param module The role's module.
     */
    void registerModule(Module module);

    /**
     * Get the module by module type.
     *
     * @param clz the module's type.
     * @return Return the module instance.
     */
    <T extends Module> T module(Class<T> clz);
}
