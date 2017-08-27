package org.okraAx.internal.events;

import org.junit.Test;

/**
 * @author TinyZ.
 * @version 2017.08.19
 */
public class EventTest {

//        SimpleChannelInboundHandler

    @Test
    public void test() {
        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.addEventListener("x", new SimpleEventListener<StringEvent>() {
            @Override
            public void fireEvent0(StringEvent event) {
                if (event instanceof StringEvent) {
                    System.out.println("" + ((StringEvent) event).param);
                } else {
                    System.out.println(event.getType());
                }
            }
        });
        dispatcher.dispatchEvent(new Event("x"));
        dispatcher.dispatchEvent(new StringEvent("x", this, "Msg"));

    }

    public class StringEvent extends Event {

        public Object trigger;
        public String param;

        public StringEvent(String type, Object trigger, String param) {
            super(type);
            this.trigger = trigger;
            this.param = param;
        }
    }

}
