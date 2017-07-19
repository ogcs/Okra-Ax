package org.okraAx.internal.bean;

/**
 * @author TinyZ.
 * @version 2017.06.21
 */
public class ConnectionInfo {

    private int id; //  唯一ID
    private String name; //  名称
    private String host;    //  目标host
    private int port;   //  目标端口
    private long version;   //  版本
    private long security;    //  校验密钥

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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getSecurity() {
        return security;
    }

    public void setSecurity(long security) {
        this.security = security;
    }
}
