package org.okraAx.login.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.internal.bean.ConnectionInfo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.05.21
 */
@Service
public class RemoteComponent {

    private static final Logger LOG = LogManager.getLogger(RemoteComponent.class);

    private Map<Integer, Object> channels = new ConcurrentHashMap<>();


    /**
     * 注册remote节点
     */
    public boolean verifyNode(ConnectionInfo info) {
        if (info == null) {
            LOG.error("[remote] node info is null.");
            return false;
        }
        //  密钥 -
        //  版本号



        return true;
    }
















}
