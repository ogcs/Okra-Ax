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

package org.ogcs.ax.component;

/**
 * Ax Component Information.
 * Include component's host and listen port
 */
public class AxCoInfo {

    private long id;  //  组件唯一ID
    private String host;    //  组件的host地址
    private int port;       //  组件内部监听端口
    private int bind;       //  组件绑定外部监听端口

    public AxCoInfo() {
    }

    public AxCoInfo(long id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public AxCoInfo(long id, String host, int port, int bind) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.bind = bind;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }
}
