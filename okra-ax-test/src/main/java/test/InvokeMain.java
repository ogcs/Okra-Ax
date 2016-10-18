package test;

import com.google.protobuf.ByteString;
import org.ogcs.gpb.generated.GpbChess;
import org.ogcs.gpb.generated.GpbChess.ReqChessJoin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/10
 * @since 1.0
 */
public class InvokeMain {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReqChessJoin reqChessJoin = ReqChessJoin.newBuilder()
                .setRoomId(1)
                .setName("Room1")
                .build();

        ReqChessJoin invoke = invoke(ReqChessJoin.class, reqChessJoin.toByteString());

        System.out.println();
    }


    public static <T> T invoke(Class<T> clz, Object... params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clz.getMethod("parseFrom", ByteString.class);
        Object invoke = method.invoke(null, params);
        if (invoke.getClass().isAssignableFrom(clz)) {
            return (T)invoke;
        }
        return null;
    }
}
