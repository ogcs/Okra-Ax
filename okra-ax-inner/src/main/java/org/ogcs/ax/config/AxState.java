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

package org.ogcs.ax.config;

/**
 * Defined inner state
 */
public interface AxState {

    int STATE_1_UNKNOWN_COMMAND = 1;  //  未知的command
    int STATE_2_AUTH_ERROR = 2;  //  授权信息错误
    int STATE_3_REQUEST_TIMEOUT = 3;  //  内部请求超时
    int STATE_4_SERVER_GONE_AWAY = 4;  //  服务器连接丢失

}
