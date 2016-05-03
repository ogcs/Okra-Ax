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

package org.ogcs.ax.component;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2015/12/24
 */
public interface SpringContext {

    // Manager
    String MODULE_USER_MANAGER = "userManager";

    String MODULE_ROOM_MANAGER = "roomManager";


    String MANAGER_CONNECTOR = "connectorManager";
    String MANAGER_AX_COMPONENT = "axCoManager";
    String MANAGER_REMOTE = "remoteManager";



    String TRANSACTION_TEMPLATE = "txTemplate";

    // Util Module
    String MODULE_REDIS_UTIL = "redisUtil";

    // Mapper
    String MAPPER_ACCOUNT = "accountMapper";



}
