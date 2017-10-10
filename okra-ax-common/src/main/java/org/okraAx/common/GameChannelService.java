package org.okraAx.common;

/**
 * remote service.
 *
 * <pre>
 *     1. 通用游戏频道服务
 * </pre>
 *
 * @author TinyZ.
 * @version 2017.09.27
 */
public interface GameChannelService {

    //  service

    void enterChannel();

    //  callback

    void callbackVerifyPlayerAuth(int ret);

    void callbackRegister(int ret);

}
