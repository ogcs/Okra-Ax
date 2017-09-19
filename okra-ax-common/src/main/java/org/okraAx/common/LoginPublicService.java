package org.okraAx.common;

/**
 * @author TinyZ
 * @version 2017.03.12
 */
public interface LoginPublicService {

    void onCreateRole(String openId, String name, int figure);

    void onLogin(String openId);

    void onSyncTime();

    void onShowChannelList();

    void onEnterChannel();
}
