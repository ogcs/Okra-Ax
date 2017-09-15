package org.okraAx.test;

import org.junit.Test;
import org.okraAx.common.LogicService;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.utilities.ProxyUtil;
import org.okraAx.utilities.ReflectionUtil;

/**
 * @author TinyZ.
 * @version 2017.09.15
 */
public class ReflectionUtilTest {

    @Test
    public void test() throws ClassNotFoundException {

        LogicService ins = ProxyUtil.newProxyInstance(LogicService.class, (proxy, method, args) -> {
            //  no-op
            System.out.println(method.getName());
            return null;
        });


        ProxyClient<LogicService> client = new ProxyClient<>(null, null, null);


        Class<LogicService> logicServiceClass = ReflectionUtil.tryGetGenericInterface(ins);


        System.out.println();

        System.out.println();

        System.out.println();

        System.out.println();

        System.out.println();

    }
}