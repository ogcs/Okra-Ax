package org.okraAx.login.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.internal.bean.ConnectionInfo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.06.21
 */
@Service
public class LogicComponent {

    private static final Logger LOG = LogManager.getLogger(LogicComponent.class);

    private Map<Integer, Object> logicMap = new ConcurrentHashMap<>();

    public void registerLogic(ConnectionInfo info) {
        if (info == null) {
            return;
        }
        info.getVersion();

    }

    public void logicClient(long uid) {

    }







}
