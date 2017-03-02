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

package org.okraAx.logic.impl;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/26
 */
public interface Modules {

    String MODULE_GATE = "gate";    //  网关模块
    String MODULE_LOGIN = "login";   //  登录模块
    String MODULE_CHAT = "chat";    //  聊天模块
    String MODULE_CHESS = "chess";   //  象棋模块

    static String module(int module) {
        switch (module) {
            case 1:
                return MODULE_GATE;
            case 2:
                return MODULE_LOGIN;
            case 3:
                return MODULE_CHAT;
            case 4:
                return MODULE_CHESS;
            default:
                return "";
        }
    }
}
