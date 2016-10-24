package org.ogcs.ax.logic;

import org.ogcs.ax.component.bean.AxCoInfo;
import org.ogcs.ax.component.inner.AxInnerServer;
import org.ogcs.ax.component.zookeeper.AxZookeeper;
import org.ogcs.ax.config.AxProperties;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Commands;

/**
 * @author TinyZ
 * @date 2016-10-21.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        // zookeeper
        AxCoInfo info = new AxCoInfo(AxProperties.axId, AxProperties.axHost, AxProperties.axPort, AxProperties.axBind);
        AxZookeeper axZk = new AxZookeeper(AxProperties.axZkConnectString, AxProperties.axZkTimeout, AxProperties.axZkWatches, AxProperties.axZkRootPath, AxProperties.axModule, AxProperties.axId, info);
        axZk.init();

        String id = String.valueOf(AxProperties.axId);
        // inner
        Commands.register();
        AxInnerServer inner = new AxInnerServer(id, AxProperties.axPort);
        inner.start();
        // gate
        if (AxProperties.axBind > 0) {
            GateCommands.INSTANCE.initialize();
            AxGate gate = new AxGate(id, AxProperties.axBind);
            gate.start();
        } else if (AxProperties.axLoginPort > 0) {
            // 启动外部服务器
            HttpServer login = new HttpServer(AxProperties.axLoginPort);
            login.start();
        }
    }
}
