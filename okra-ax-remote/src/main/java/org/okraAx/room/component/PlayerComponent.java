package org.okraAx.room.component;

import org.okraAx.room.bean.RmUserInfoBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.04.09
 */
public class PlayerComponent {

    private Map<String /* security code */, Long /* uid */> securityCodeMap = new ConcurrentHashMap<>();

    private Map<Long /* uid */, RmUserInfoBean> userInfoMap = new ConcurrentHashMap<>();



    public void registerUserInfo(RmUserInfoBean userInfo) {

    }



















}
