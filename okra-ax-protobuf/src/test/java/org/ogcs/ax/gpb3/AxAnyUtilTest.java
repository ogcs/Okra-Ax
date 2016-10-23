package org.ogcs.ax.gpb3;

import org.junit.Test;
import org.ogcs.ax.gpb3.OkraAx.AxCall;
import org.ogcs.ax.gpb3.OkraAx.AxInbound;
import org.ogcs.ax.utilities.AxAnyUtil;

/**
 * @author TinyZ
 * @date 2016-10-24.
 */
public class AxAnyUtilTest {

    @Test
    public void test() {
//        GpbReplys.response()
        AxInbound build = AxInbound.newBuilder().build();
        for (int i = 0; i < 10000000; i++) {
            AxAnyUtil.fetchMsgId(build);
        }
    }

    @Test
    public void test1() {
        AxCall build = AxCall.newBuilder().build();
        for (int i = 0; i < 10000000; i++) {
            AxAnyUtil.fetchMsgId(build);
        }
    }










}
