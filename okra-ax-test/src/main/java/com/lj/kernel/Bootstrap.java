package com.lj.kernel;

import com.lj.kernel.gate.AxGate;
import com.lj.kernel.login.HttpServer;
import com.lj.kernel.remote.Commands;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.AxProperties;
import org.ogcs.ax.component.inner.AxInnerServer;
import org.ogcs.ax.component.zookeeper.AxZookeeper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/12
 * @since 1.0
 */
public class Bootstrap {

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
            com.lj.kernel.gate.command.Commands.INSTANCE.initialize();
            AxGate gate = new AxGate(id, AxProperties.axBind);
            gate.start();
        } else if (AxProperties.axLoginPort > 0) {
            // 启动外部服务器
            HttpServer login = new HttpServer(AxProperties.axLoginPort);
            login.start();
        }
    }
}
