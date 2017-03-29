package org.okraAx.login.bean;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
public final class ChannelInfo {

    private int roomId;
    private int type;
    private long version;
    private String host;
    private int port;

    public ChannelInfo(int roomId, int type, long version, String host, int port) {
        this.roomId = roomId;
        this.type = type;
        this.version = version;
        this.host = host;
        this.port = port;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getType() {
        return type;
    }

    public long getVersion() {
        return version;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "roomId=" + roomId +
                ", type=" + type +
                ", version=" + version +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
