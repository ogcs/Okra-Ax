package org.okraAx.internal.events;

/**
 * @author TinyZ.
 * @version 2017.08.19
 */
public interface EventListener {


    void fireEvent(Event event) throws Exception;

}
