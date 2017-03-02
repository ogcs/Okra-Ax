package org.okraAx.logic.impl;

import org.okraAx.utilities.GpbReplys;
import org.ogcs.app.Session;
import org.okraAx.internal.core.AxCallback;
import org.okraAx.v3.OkraAx.AxOutbound;
import org.okraAx.v3.GpbD;
import org.okraAx.v3.OkraAx;

/**
 * Asynchronous operation will be invoked when remote service {@link GpbD.Response} or request timeout.
 * Send message to user.
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class GenericCallback implements AxCallback<AxOutbound> {

    private Session session;
    private GpbD.Request request;

    public GenericCallback(Session session, GpbD.Request request) {
        this.session = session;
        this.request = request;
    }

    @Override
    public void run(OkraAx.AxOutbound msg) {
        GpbD.Response response;
        if (msg.hasError()) {
            OkraAx.AxError error = msg.getError();
            if (error.getMsg().isEmpty()) {
                response = GpbReplys.error(request.getId(), error.getState(), error.getMsg());
            } else {
                response = GpbReplys.error(request.getId(), error.getState());
            }
        } else {
            response = GpbReplys.response(request.getId(), msg);
        }
        session.writeAndFlush(response);
    }
}
