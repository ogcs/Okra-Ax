package test;

import com.lj.kernel.ax.AxCoInfo;
import com.lj.kernel.ax.Modules;
import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.gate.RemoteManager;
import com.lj.kernel.gate.AxGate;
import org.ogcs.app.AppContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class Gate1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        String gateId = "Gate1";

        // 注册远程节点  - module: 1[chat]
        List<AxCoInfo> list = new ArrayList<>();
        list.add(new AxCoInfo("R0", "127.0.0.1", 9000));
        list.add(new AxCoInfo("R1", "127.0.0.1", 9001));
        list.add(new AxCoInfo("R2", "127.0.0.1", 9002));
        RemoteManager remoteManager = (RemoteManager) AppContext.getBean(SpringContext.MANAGER_REMOTE);
        for (AxCoInfo axCoInfo : list) {
            remoteManager.add(gateId, String.valueOf(Modules.MODULE_CHAT), axCoInfo.getId(), axCoInfo.getHost(), axCoInfo.getPort());
            remoteManager.add(gateId, String.valueOf(Modules.MODULE_CHESS), axCoInfo.getId(), axCoInfo.getHost(), axCoInfo.getPort());
        }
        // 启动服务
        AxGate gate = new AxGate(gateId, 10001);
        gate.start();


    }
}
