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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务器参数配置
 *
 * @author Tiny&zzh 2014-4-8
 */
public class ServerProperties {

    private static String fileName = "server.properties";
    // --------------------------------------------------------------------------------------
    public static int serverId = 1;

    private static String policyFile = "flash-policy.xml";

    public static String gameRedisHost = "127.0.0.1";
    public static int gameRedisPort = 6379;
    public static int gameRedisDB = 5;
    public static String gameRedisPwd = null;
    public static byte[] AES_KEY_BYTE = "".getBytes();
    public static String AES_KEY = "";
    public static int LOGIC_SERVER_PORT = 9008;

    public static String OP_API_URL = "http://127.0.0.1/";

    public static String SYSTEM_SERVER_IDENTIFY = ""; // 系统访问权限


    public static String API_KEY = "LINGJINGLINGJING";

    // --------------------------------------------------------------------------------------

//    static {
//        String filePath = new File("").getAbsolutePath() + "/conf/" + File.separator + fileName;
//
//        // Load properties
//        Properties props = new Properties();
//        try {
//            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
//            props.load(in);
//            serverId = Integer.parseInt(props.getProperty("server.id", String.valueOf(serverId)));
//            policyFile = props.getProperty("flash.policy.file.path", policyFile);
//            LOG.info("Server properties load success.");
//        } catch (IOException e) {
//            LOG.warn("Server properties load failed.");
//            e.printStackTrace();
//        }
//    }

    public static void load() {
        // System.setProperties();
        //System.setProperty("jet.lanes", jetLanes);
        System.setProperty("flash.policy.file.path", policyFile);
    }

    // --------------------------------------------------------------------------------------

    private static AtomicInteger ids = new AtomicInteger(0);

    /**
     * 生成全服唯一ID
     */
    public static long id() {
        return (((long) (ServerProperties.serverId & 0xFFFF)) << 48) | (((System.currentTimeMillis() / 1000) & 0x00000000FFFFFFFFL) << 16) | (ids.getAndIncrement() & 0x0000FFFF);
    }
}
