package org.okraAx.internal.events;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.08.19
 */
public class EventDispatcher implements IEventDispatcher {

    protected Map<String, Set<EventListener>> listeners = new ConcurrentHashMap<>();

    @Override
    public void addEventListener(String type, EventListener listener) {
        addEventListener(type, listener, false, 1, false);
    }

    @Override
    public void addEventListener(String type, EventListener listener, boolean useCapture, int priority, boolean useWeakReference) {
        Set<EventListener> sets = listeners.get(type);
        if (sets == null) {
            sets = Collections.newSetFromMap(new ConcurrentHashMap<>());
            listeners.put(type, sets);
        }
        sets.add(listener);
    }

    @Override
    public boolean dispatchEvent(Event event) {
        Set<EventListener> set = listeners.get(event.getType());
        if (set != null) {
            for (EventListener listener : set) {
                try {
                    listener.fireEvent(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasEventListener(String type) {
        return listeners.containsKey(type);
    }

    @Override
    public void removeEventListener(String type) {
        listeners.remove(type);
    }

    @Override
    public void removeEventListener(String type, EventListener listener) {
        Set<EventListener> sets = listeners.get(type);
        if (sets != null) {
            sets.remove(listener);
        }
    }

    @Override
    public boolean willTrigger(String type) {
        return false;
    }
}
