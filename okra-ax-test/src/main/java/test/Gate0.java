package test;

import org.ogcs.ax.gate.AxGate;
import org.ogcs.ax.gate.command.GateCommands;
import org.ogcs.app.AppContext;
import org.ogcs.ax.component.bean.AxCoInfo;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.component.inner.AxInnerServer;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 网关服务器0
 */
public class Gate0 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        String gateId = "Gate0";
        long local = 100L;

        AxInnerCoManager axCoManager = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
        // 注册远程节点  - module: 1[chat]
        AxDatas.map.forEach((module, list) -> {
            for (AxCoInfo axCoInfo : list) {
                if (axCoInfo.getId() != local)
                    axCoManager.add(module, local, axCoInfo);
            }
        });

        // 启动内部服务器
        AxInnerServer inner = new AxInnerServer(String.valueOf(local), 8000);
        inner.start();
        // 启动外部服务器
        GateCommands.INSTANCE.initialize();
        AxGate gate = new AxGate(gateId, 10000);
        gate.start();
    }
}
