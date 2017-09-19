package org.okraAx.internal.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.utilities.TypeParameterMatcher;


/**
 * @author TinyZ.
 * @version 2017.08.19
 */
public abstract class SimpleEventListener<E extends Event> implements EventListener {

    private static final Logger LOG = LogManager.getLogger(SimpleEventListener.class);
    private final TypeParameterMatcher matcher;

    public SimpleEventListener() {
        matcher = TypeParameterMatcher.find(this, SimpleEventListener.class, "E");
    }

    @Override
    public void fireEvent(Event event) {
        if (matcher.match(event)) {
            @SuppressWarnings("unchecked")
            E var = (E) event;
            this.fireEvent0(var);
        } else {
            LOG.warn("Unknown event type {}.", event.getType());
        }
    }

    public abstract void fireEvent0(E event);
}
