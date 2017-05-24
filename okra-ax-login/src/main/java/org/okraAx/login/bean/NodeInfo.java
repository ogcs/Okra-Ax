package org.okraAx.login.bean;

/**
 * @author TinyZ.
 * @version 2017.05.21
 */
public class NodeInfo {

    private int id;             //  节点ID
    private String name;        //  节点名称
    private String host;        //  IP
    private int port;           //  端口
    private String security;    //  校验密钥
    private long version;       //  版本号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", security='" + security + '\'' +
                ", version=" + version +
                '}';
    }
}
