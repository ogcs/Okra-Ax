package org.okraAx.login.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TinyZ.
 * @version 2017.05.14
 */
public final class LoginConfig {

    private static final Logger LOG = LogManager.getLogger(LoginConfig.class);
    private static final AtomicInteger IDS = new AtomicInteger(0);
    /**
     *
     */
    private static String FILE_PATH = new File("").getAbsolutePath() + "/config/" + File.separator + "config.properties";
    // --------------------------------------------------------------------------------------
    /**
     * 登录服ID - 用于生成全区全服唯一ID
     */
    private static int loginServerId = 0;

    static {
        load();
    }

    /**
     * Load Server Config.
     */
    public static void load() {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(FILE_PATH));
            props.load(in);
            loginServerId = Integer.parseInt(props.getProperty("server.id", String.valueOf(loginServerId)));

            LOG.info("Server properties load success.");
        } catch (IOException e) {
            LOG.error("Server properties load failed.", e);
        }
    }

    /**
     * 生成全服唯一ID
     */
    public static long id() {
        return (((long) (loginServerId & 0xFFFF)) << 48)
                | (((System.currentTimeMillis() / 1000) & 0x00000000FFFFFFFFL) << 16)
                | (IDS.getAndIncrement() & 0x0000FFFF);
    }

}
