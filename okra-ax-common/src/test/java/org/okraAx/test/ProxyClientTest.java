package org.okraAx.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.okraAx.common.LogicService;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
import org.okraAx.utilities.ProxyUtil;

/**
 * @author TinyZ.
 * @version 2017.09.14
 */
public class ProxyClientTest {

    private static final Logger LOG = LogManager.getLogger(ProxyClientTest.class);

    @Test
    public void test() throws ClassNotFoundException {
        LogicService defaultBean = (LogicService) ProxyUtil.newProxyInstance(LogicService.class, (proxy, method, args) -> {
            //  no-op
            LOG.info("Empty proxy instance invoked by [{}]. args:{}", method.getName(), args);
            return null;
        });
        NetSession session = new NetSession(null);
        ProxyClient<LogicService> bean = new ProxyClient<>(session, new GpbInvocationHandler(session), defaultBean);
        bean.initialize();
        LogicService client = bean.impl();
        System.out.println();
    }









}