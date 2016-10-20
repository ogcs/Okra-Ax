package test.thread;

import org.ogcs.ax.component.inner.BlockingCallback;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/16
 * @since 1.0
 */
public class ThreadMain {

    public static void main(String[] args) {

        BlockingCallback<String> callback = new BlockingCallback<>();
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callback.run("Good");
        }).start();
        synchronized (callback) {
            try {
                callback.wait(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Timeout");
            if (!callback.isDone()) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Received:" + callback.get());


        System.out.println();
    }
}
