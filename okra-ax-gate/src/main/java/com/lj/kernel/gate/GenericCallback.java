package com.lj.kernel.gate;

import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.GpbD.Response;
import org.ogcs.app.Session;
import org.ogcs.ax.component.AxCallback;
import org.ogcs.ax.component.GpbReplys;
import org.ogcs.ax.gpb.OkraAx.AxError;
import org.ogcs.ax.gpb.OkraAx.AxOutbound;

/**
 * Generic callback for send {@link Response} to user.
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class GenericCallback implements AxCallback<AxOutbound> {

    private Session session;
    private Request request;

    public GenericCallback(Session session, Request request) {
        this.session = session;
        this.request = request;
    }

    @Override
    public void run(AxOutbound msg) {
        Response response;
        if (msg.hasError()) {
            AxError error = msg.getError();
            if (error.hasMsg()) {
                response = GpbReplys.error(request.getId(), error.getState(), error.getMsg());
            } else {
                response = GpbReplys.error(request.getId(), error.getState());
            }
        } else {
            response = GpbReplys.response(request.getId(), msg.getData());
        }
        session.writeAndFlush(response);
    }
}