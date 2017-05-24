package org.okraAx.login.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.login.bean.NodeInfo;

/**
 * @author TinyZ.
 * @version 2017.05.21
 */
public class RemoteComponent {

    private static final Logger LOG = LogManager.getLogger(LoginComponent.class);

    /**
     * 注册remote节点
     */
    public boolean verifyNode(NodeInfo info) {
        if (info == null) {
            LOG.error("[remote] node info is null.");
            return false;
        }
        //  密钥 -
        //  版本号



        return true;
    }
















}
