package org.okraAx.internal.inner.axrpc;

import org.ogcs.app.NetSession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Inner组件管理器.
 * 管理和协调内部的各个组件, 派发注册/断线等组件信息给监听服务的逻辑.
 * @author TinyZ
 * @date 2017-02-11.
 */
@Service
public class IrCopManager {


    private Map<String, NetSession> components = new ConcurrentHashMap<>();





}
