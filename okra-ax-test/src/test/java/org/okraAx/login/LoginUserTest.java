package org.okraAx.login;

import com.google.protobuf.Descriptors;
import org.junit.Test;
import org.okraAx.v3.login.beans.ProLoginBeans;

import java.util.List;

/**
 * @author TinyZ.
 * @version 2017.05.14
 */
public class LoginUserTest {

    @Test
    public void test() {
//        LoginUser user = new LoginUser();

        Descriptors.FileDescriptor descriptor = ProLoginBeans.getDescriptor();

        List<Descriptors.Descriptor> messageTypes = descriptor.getMessageTypes();


        System.out.println();

    }

}
