package org.okraAx.internal.events;

/**
 * @author TinyZ.
 * @version 2017.08.20
 */
public interface IEventDispatcher {

    void addEventListener(String type, EventListener listener);

    void addEventListener(String type, EventListener listener, boolean useCapture, int priority, boolean useWeakReference);

    boolean dispatchEvent(Event event);

    boolean hasEventListener(String type);

    void removeEventListener(String type);

    void removeEventListener(String type, EventListener listener);

    boolean willTrigger(String type);
}
