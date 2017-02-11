package org.ogcs.ax.component;

import org.ogcs.ax.component.bean.AxCoInfo;
import org.ogcs.ax.component.inner.AxInnerServer;
import org.ogcs.ax.component.zookeeper.AxZookeeper;
import org.ogcs.ax.config.AxConfig;
import org.ogcs.ax.config.AxProperties;

/**
 * @author TinyZ
 */
public class InnerFramework {

    private AxZookeeper zookeeper;
    private AxInnerServer inner;
    private AxConfig config;
    private AxCoInfo info;

    public void start() {
        try {
            //  zookeeper service
            zookeeper = new AxZookeeper(AxProperties.axZkConnectString, AxProperties.axZkTimeout, AxProperties.axZkWatches, AxProperties.axZkRootPath, AxProperties.axModule, AxProperties.axId, info);
            zookeeper.init();
            //  inner service
            String id = String.valueOf(AxProperties.axId);
            inner = new AxInnerServer(id, AxProperties.axPort);
            inner.start();
        } catch (Exception e) {
            zookeeper.close();
        }
    }

    public void stop() {
        if (inner != null)
            inner.stop();
    }

}
