package test;

import com.lj.kernel.ax.inner.AxInnerServer;
import com.lj.kernel.remote.Commands;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class Remote1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        Commands.register();
        AxInnerServer inner = new AxInnerServer("501", 9001);
        inner.start();
    }
}
