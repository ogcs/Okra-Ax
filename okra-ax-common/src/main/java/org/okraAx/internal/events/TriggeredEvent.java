package org.okraAx.internal.events;

/**
 * @author TinyZ.
 * @version 2017.08.20
 */
public class TriggeredEvent<T, P> extends Event {

    private final T trigger;
    private final P param;

    public TriggeredEvent(String type, T trigger, P param) {
        super(type);
        this.trigger = trigger;
        this.param = param;
    }

    public T getTrigger() {
        return trigger;
    }

    public P getParam() {
        return param;
    }
}
