package org.okraAx.internal.zookeeper;

/**
 * ZooKeeper Config.
 * @author TinyZ
 * @date 2017-01-18.
 */
public class ZkConfig {

    private String url;
    private int timeout;
    private String[] watches;
    private String root;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String[] getWatches() {
        return watches;
    }

    public void setWatches(String[] watches) {
        this.watches = watches;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
