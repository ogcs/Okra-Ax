package org.ogcs.ax.gpb3;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Assert;
import org.junit.Test;
import org.ogcs.ax.gpb3.AxAnyProto.AxAny;
import org.ogcs.ax.gpb3.GpbExampleProto.ExFoo;
import org.ogcs.ax.utilities.AxAnyUtil;

/**
 * @author TinyZ
 * @date 2016-10-21.
 */
public class AcTest {

    @Test
    public void testSample() throws InvalidProtocolBufferException {
        ExFoo build = ExFoo.newBuilder().setId(100).build();
        AxAny any = AxAnyUtil.pack(build);
        //  ...
        ExFoo unpack = AxAnyUtil.unpack(any, ExFoo.class);
        Assert.assertEquals(unpack, build);
    }

}
