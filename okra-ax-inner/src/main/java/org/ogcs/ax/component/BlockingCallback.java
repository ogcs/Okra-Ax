package org.ogcs.ax.component;

public class BlockingCallback<T> implements AxCallback<T> {

    private T msg;
    private boolean isDone;

    @Override
    public void run(T msg) {
        this.msg = msg;
        synchronized (this) {
            isDone = true;
            notifyAll();
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public T get() {
        return msg;
    }
}
