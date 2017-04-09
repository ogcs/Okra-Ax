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

package org.okraAx.internal.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务器配置
 */
public final class ServerProperties {

    private static final Logger LOG = LogManager.getLogger(AxProperties.class);

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    private static final String filePath = new File("").getAbsolutePath() + "/conf/" + File.separator + "ax.properties";
    private static boolean initialized = false;

    // 组件配置
    public volatile static long axId = 1;
    public volatile static String axHost = "127.0.0.1";
    public volatile static int axPort = 9000;
    public volatile static int axBind = 0;
    public volatile static String axInnerAuth = "";
    public volatile static int axLoginPort = 0;

    static {
        if (!initialized) {
            load();
            initialized = true;
        }
    }

    public static void load() {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            // set
            axId = Long.valueOf(props.getProperty("ax.id", String.valueOf(axId)));
            axHost = props.getProperty("ax.host", axHost).toLowerCase();
            axPort = Integer.parseInt(props.getProperty("ax.port", String.valueOf(axPort)));
            axBind = Integer.parseInt(props.getProperty("ax.bind", String.valueOf(axBind)));
            axInnerAuth = props.getProperty("ax.inner.auth", axInnerAuth).toLowerCase();
            axLoginPort = Integer.parseInt(props.getProperty("ax.login.port", String.valueOf(axLoginPort)));

            LOG.info("server properties load success.");
        } catch (IOException e) {
            LOG.warn("server properties load failed.", e);
        }
    }

    /**
     * 生成全服唯一ID
     */
    public static long id() {
        return ((AxProperties.axId & 0xFFFF) << 48)
                | (((System.currentTimeMillis() / 1000) & 0x00000000FFFFFFFFL) << 16)
                | (ATOMIC_INTEGER.getAndIncrement() & 0x0000FFFF
        );
    }
}
