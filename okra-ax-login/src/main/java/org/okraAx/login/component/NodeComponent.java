package org.okraAx.login.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.LogicService;
import org.okraAx.internal.bean.ConnectionInfo;
import org.okraAx.internal.net.NetSession;
import org.okraAx.login.server.LogicClient;
import org.okraAx.utilities.NetHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 节点组件
 *
 * @author TinyZ.
 * @version 2017.06.21
 */
@Service
public class NodeComponent {

    private static final Logger LOG = LogManager.getLogger(NodeComponent.class);

    private Map<Integer, LogicClient> logicClientMap = new ConcurrentHashMap<>();

    public void registerNode(ConnectionInfo info) {
        if (info == null) {
            return;
        }
        info.getVersion();

        //  TODO:校验通过

        if (info.getType() == 1) {
            NetSession session = NetHelper.session();
            LogicClient client = new LogicClient(session, info);


        }


        //


    }

    //  获取logicClient
    public LogicService logicClient(long uid) {
        return logicClientMap.get(uid % 3).logicClient();
    }





}
