package org.okraAx.internal.events;

/**
 * @author TinyZ.
 * @version 2017.08.19
 */
public class Event {

    /**
     * 事件类型
     */
    private final String type;

    public Event(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
