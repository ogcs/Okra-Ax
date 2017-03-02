package org.okraAx.test;

import org.junit.Test;
import org.ogcs.app.Procedure;
import org.okraAx.room.fy.impl.FyChessServiceImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author TinyZ
 * @date 2017-02-24.
 */
public class Test1 {

    @Test
    public void test1() {
        Method[] methods = FyChessServiceImpl.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("onShowRoomStatus")) {
                Annotation[] annotations = method.getAnnotations();
                Procedure annotation = method.getAnnotation(Procedure.class);

                System.out.println();

            }
        }


    }

}
