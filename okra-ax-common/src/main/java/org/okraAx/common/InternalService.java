package org.okraAx.common;

/**
 * @author TinyZ.
 * @version 2017.03.13
 */
public interface InternalService {

    /**
     * 校验节点信息和授权
     *
     * @param key     安全密钥
     * @param version 版本信息
     */
    void onNodeActive(String key, String version);
    void onNodeInActive(String key, String version);

}
