package org.okraAx.internal.v3.protobuf;

import com.google.protobuf.Message;
import org.junit.Assert;
import org.junit.Test;
import org.okraAx.v3.GpcRelay;
import org.okraAx.v3.GpcVoid;

import java.lang.reflect.Method;

/**
 * @author TinyZ.
 * @version 2017.10.01
 */
public class GpbMessageDescTest {

    @Test
    public void unpackByteString() throws Exception {
        GpcRelay bean = GpcRelay.newBuilder().setSource(0L)
                .setData(
                        GpcVoid.getDefaultInstance().toByteString()
                ).build();

        GpbMessageDesc desc = new GpbMessageDesc(GpcRelay.getDescriptor());
        Message unpack = desc.unpack(bean.toByteString());

        Method method = this.getClass().getDeclaredMethod("mockMethod", long.class, Integer.class);
        Object[] objects = desc.unpackWithJavaMethod(method, bean.toByteString());
        Object invoke = method.invoke(this, objects);
        Assert.assertTrue(unpack.equals(bean));
    }

    private void mockMethod(long var0, Integer var1) {
        System.out.println("mockMethod var0:" + var0 + ", var1:" + var1);
    }

    @Test
    public void unpackBytes() throws Exception {
    }

    @Test
    public void pack() throws Exception {
    }

}