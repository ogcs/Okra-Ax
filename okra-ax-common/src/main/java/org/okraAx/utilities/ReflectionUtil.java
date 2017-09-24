package org.okraAx.utilities;

import java.lang.reflect.Type;

/**
 * @author TinyZ.
 * @version 2017.09.15
 */
public class ReflectionUtil {

    private ReflectionUtil() {
        //  no-op
    }

    /**
     * <pre>
     *      1.
     *  </pre>
     */
    public static <T> Class<T> tryGetGenericInterface(T obj) throws ClassNotFoundException {
        Type[] genericInterfaces = obj.getClass().getGenericInterfaces();
        if (genericInterfaces == null || genericInterfaces.length <= 0)
            throw new IllegalArgumentException(String.format("%s's generic interfaces count is zero.", obj.getClass()));
        return (Class<T>) Class.forName(genericInterfaces[0].getTypeName());
    }
}
