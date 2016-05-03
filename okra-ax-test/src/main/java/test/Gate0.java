package test;

import com.lj.kernel.gate.AxGate;
import org.ogcs.app.AppContext;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.Modules;
import org.ogcs.ax.component.SpringContext;
import org.ogcs.ax.component.inner.AxInnerServer;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class Gate0 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        String gateId = "Gate0";
        long local = 100L;

        AxInnerCoManager axCoManager = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
        // 注册远程节点  - module: 1[chat]
        Map<String, List<AxCoInfo>> map = new HashMap<String, List<AxCoInfo>>() {{
            put(String.valueOf(Modules.MODULE_CHAT), new ArrayList<AxCoInfo>() {{
                add(new AxCoInfo("500", "127.0.0.1", 9000));
                add(new AxCoInfo("501", "127.0.0.1", 9001));
            }});
            put(String.valueOf(Modules.MODULE_CHESS), new ArrayList<AxCoInfo>() {{
                add(new AxCoInfo("600", "127.0.0.1", 9000));
                add(new AxCoInfo("601", "127.0.0.1", 9001));
                add(new AxCoInfo("602", "127.0.0.1", 9002));
            }});
            put(String.valueOf(Modules.MODULE_GATE), new ArrayList<AxCoInfo>() {{
                add(new AxCoInfo("100", "127.0.0.1", 8000));
                add(new AxCoInfo("101", "127.0.0.1", 8001));
            }});
        }};
        map.forEach((module, list) -> {
            for (AxCoInfo axCoInfo : list) {
                axCoManager.add(module, Long.valueOf(axCoInfo.getId()), local, axCoInfo.getHost(), axCoInfo.getPort());
            }
        });

        // 启动内部服务器
        AxInnerServer inner = new AxInnerServer(String.valueOf(local), 8000);
        inner.start();
        // 启动外部服务器
        AxGate gate = new AxGate(gateId, 10000);
        gate.start();
    }
}
