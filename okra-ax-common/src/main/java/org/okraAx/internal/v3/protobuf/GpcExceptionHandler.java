package org.okraAx.internal.v3.protobuf;

import com.lmax.disruptor.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.concurrent.ConcurrentEvent;

/**
 * @author TinyZ.
 * @version 2017.10.02
 */
final class GpcExceptionHandler implements ExceptionHandler<ConcurrentEvent> {

    private static final Logger LOG = LogManager.getLogger(GpcExceptionHandler.class);

    @Override
    public void handleEventException(Throwable ex, long sequence, ConcurrentEvent event) {

    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }
}
