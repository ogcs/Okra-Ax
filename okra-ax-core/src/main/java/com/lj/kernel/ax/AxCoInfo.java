package com.lj.kernel.ax;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/21
 */
public class AxCoInfo {

    private String id;  //  组件唯一ID
    private String host;    //  组件的host地址
    private int port;       //  组件内部监听端口
    private int bind;       //  组件绑定外部监听端口

    public AxCoInfo() {
    }

    public AxCoInfo(String id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public AxCoInfo(String id, String host, int port, int bind) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.bind = bind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }
}
