package org.okraAx.login.bean;

/**
 * @author TinyZ.
 * @version 2017.06.13
 */
public class VoItem extends ChangeableBean {

    private long itemId;    //  唯一ID
    private int cfgItemId;  //  配置表ID
    private int amount;     //  数量
    private int expire;     //  过期

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getCfgItemId() {
        return cfgItemId;
    }

    public void setCfgItemId(int cfgItemId) {
        this.cfgItemId = cfgItemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
