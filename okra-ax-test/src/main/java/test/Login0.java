package test;

import com.lj.kernel.login.HttpServer;
import org.ogcs.app.AppContext;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.Modules;
import org.ogcs.ax.component.SpringContext;
import org.ogcs.ax.component.inner.AxInnerServer;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 登录服务器
 */
public class Login0 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        long local = AxDatas.MODULE_ID_LOGIN_0;

        AxInnerCoManager axCoManager = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
        // 连接全部gate节点
        List<AxCoInfo> axCoInfos = AxDatas.map.get(String.valueOf(Modules.MODULE_GATE));
        if (axCoInfos != null) {
            axCoInfos.forEach((axCoInfo) -> {
                axCoManager.add(String.valueOf(Modules.MODULE_GATE), local, axCoInfo);
            });
        }

        // 启动内部服务器
        AxInnerServer inner = new AxInnerServer(String.valueOf(local), 7000);
        inner.start();
        // 启动外部服务器
        HttpServer login = new HttpServer(11000);
        login.start();
    }
}
