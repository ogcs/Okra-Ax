package org.okraAx.login.bean;

/**
 * @author TinyZ.
 * @version 2017.07.20
 */
public class VoLottery extends ChangeableBean {

    public volatile int lotteryId;
    public volatile int energy;


    @Override
    public Object beanKey() {
        return lotteryId;
    }
}
