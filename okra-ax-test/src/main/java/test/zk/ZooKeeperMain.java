package test.zk;

import org.okraAx.internal.bean.AxCoInfo;
import org.okraAx.internal.config.AxProperties;
import org.okraAx.internal.zookeeper.AxZookeeper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/11
 * @since 1.0
 */
public class ZooKeeperMain {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        AxCoInfo info = new AxCoInfo(AxProperties.axId, AxProperties.axHost, AxProperties.axPort, AxProperties.axBind);

        AxZookeeper axZk = new AxZookeeper(AxProperties.axZkConnectString, AxProperties.axZkTimeout, AxProperties.axZkWatches, AxProperties.axZkRootPath, AxProperties.axModule, AxProperties.axId, info);
        axZk.init();

        System.out.println();
        while (true) {

        }
    }
}
