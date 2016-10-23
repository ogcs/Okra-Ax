package org.ogcs.ax.gpb3;

import com.google.protobuf.Any;
import org.junit.Test;

/**
 * @author TinyZ
 * @date 2016-10-20.
 */
public class GpbAnyTest {

    @Test
    public void testUnpack() {
//        Any pack = Any.pack(null);
//        pack.unpack()

        System.out.println(calcWorldCount("www sss www"));

    }

    public int calcWorldCount(String str) {
        return str.length() - str.replace(" ", "").length() + 1;
    }


}
