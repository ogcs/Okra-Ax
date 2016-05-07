package org.ogcs.ax.component;

/**
 * Ax回调
 */
public interface AxCallback<T> {

    /**
     * 执行Ax回调的逻辑
     *
     * @param msg 接受的消息
     */
    void run(T msg);
}
