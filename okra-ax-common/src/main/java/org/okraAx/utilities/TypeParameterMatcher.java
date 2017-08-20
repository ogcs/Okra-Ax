package org.okraAx.utilities;


import java.lang.reflect.*;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Netty 项目的TypeParameterMatcher. 避免版本迭代变更. 略微修改
 *
 * @author TinyZ.
 * @version 2017.08.20
 * @see TypeParameterMatcher
 */
public abstract class TypeParameterMatcher {

    private static final TypeParameterMatcher NOOP = new TypeParameterMatcher() {
        @Override
        public boolean match(Object msg) {
            return true;
        }
    };
    private static final ThreadLocal<Map<Class<?>, TypeParameterMatcher>> typeParameterMatcherGetCache = new ThreadLocal<>();
    private static final ThreadLocal<Map<Class<?>, Map<String, TypeParameterMatcher>>> typeParameterMatcherFindCache = new ThreadLocal<>();

    public static Map<Class<?>, TypeParameterMatcher> typeParameterMatcherGetCache() {
        Map<Class<?>, TypeParameterMatcher> cache = typeParameterMatcherGetCache.get();
        if (cache == null) {
            cache = new IdentityHashMap<Class<?>, TypeParameterMatcher>();
            typeParameterMatcherGetCache.set(cache);
        }
        return cache;
    }

    public static Map<Class<?>, Map<String, TypeParameterMatcher>> typeParameterMatcherFindCache() {
        Map<Class<?>, Map<String, TypeParameterMatcher>> cache = typeParameterMatcherFindCache.get();
        if (cache == null) {
            cache = new IdentityHashMap<Class<?>, Map<String, TypeParameterMatcher>>();
            typeParameterMatcherFindCache.set(cache);
        }
        return cache;
    }

    public static TypeParameterMatcher get(final Class<?> parameterType) {
        final Map<Class<?>, TypeParameterMatcher> getCache = typeParameterMatcherGetCache();

        TypeParameterMatcher matcher = getCache.get(parameterType);
        if (matcher == null) {
            if (parameterType == Object.class) {
                matcher = NOOP;
            }

            if (matcher == null) {
                matcher = new TypeParameterMatcher.ReflectiveMatcher(parameterType);
            }

            getCache.put(parameterType, matcher);
        }

        return matcher;
    }

    public static TypeParameterMatcher find(
            final Object object, final Class<?> parameterizedSuperclass, final String typeParamName) {

        final Map<Class<?>, Map<String, TypeParameterMatcher>> findCache = typeParameterMatcherFindCache();
        final Class<?> thisClass = object.getClass();

        Map<String, TypeParameterMatcher> map = findCache.get(thisClass);
        if (map == null) {
            map = new HashMap<String, TypeParameterMatcher>();
            findCache.put(thisClass, map);
        }

        TypeParameterMatcher matcher = map.get(typeParamName);
        if (matcher == null) {
            matcher = get(find0(object, parameterizedSuperclass, typeParamName));
            map.put(typeParamName, matcher);
        }

        return matcher;
    }

    private static Class<?> find0(
            final Object object, Class<?> parameterizedSuperclass, String typeParamName) {

        final Class<?> thisClass = object.getClass();
        Class<?> currentClass = thisClass;
        for (; ; ) {
            if (currentClass.getSuperclass() == parameterizedSuperclass) {
                int typeParamIndex = -1;
                TypeVariable<?>[] typeParams = currentClass.getSuperclass().getTypeParameters();
                for (int i = 0; i < typeParams.length; i++) {
                    if (typeParamName.equals(typeParams[i].getName())) {
                        typeParamIndex = i;
                        break;
                    }
                }

                if (typeParamIndex < 0) {
                    throw new IllegalStateException(
                            "unknown type parameter '" + typeParamName + "': " + parameterizedSuperclass);
                }

                Type genericSuperType = currentClass.getGenericSuperclass();
                if (!(genericSuperType instanceof ParameterizedType)) {
                    return Object.class;
                }

                Type[] actualTypeParams = ((ParameterizedType) genericSuperType).getActualTypeArguments();

                Type actualTypeParam = actualTypeParams[typeParamIndex];
                if (actualTypeParam instanceof ParameterizedType) {
                    actualTypeParam = ((ParameterizedType) actualTypeParam).getRawType();
                }
                if (actualTypeParam instanceof Class) {
                    return (Class<?>) actualTypeParam;
                }
                if (actualTypeParam instanceof GenericArrayType) {
                    Type componentType = ((GenericArrayType) actualTypeParam).getGenericComponentType();
                    if (componentType instanceof ParameterizedType) {
                        componentType = ((ParameterizedType) componentType).getRawType();
                    }
                    if (componentType instanceof Class) {
                        return Array.newInstance((Class<?>) componentType, 0).getClass();
                    }
                }
                if (actualTypeParam instanceof TypeVariable) {
                    // Resolved type parameter points to another type parameter.
                    TypeVariable<?> v = (TypeVariable<?>) actualTypeParam;
                    currentClass = thisClass;
                    if (!(v.getGenericDeclaration() instanceof Class)) {
                        return Object.class;
                    }

                    parameterizedSuperclass = (Class<?>) v.getGenericDeclaration();
                    typeParamName = v.getName();
                    if (parameterizedSuperclass.isAssignableFrom(thisClass)) {
                        continue;
                    } else {
                        return Object.class;
                    }
                }

                return fail(thisClass, typeParamName);
            }
            currentClass = currentClass.getSuperclass();
            if (currentClass == null) {
                return fail(thisClass, typeParamName);
            }
        }
    }

    private static Class<?> fail(Class<?> type, String typeParamName) {
        throw new IllegalStateException(
                "cannot determine the type of the type parameter '" + typeParamName + "': " + type);
    }

    public abstract boolean match(Object msg);

    private static final class ReflectiveMatcher extends TypeParameterMatcher {
        private final Class<?> type;

        ReflectiveMatcher(Class<?> type) {
            this.type = type;
        }

        @Override
        public boolean match(Object msg) {
            return type.isInstance(msg);
        }
    }

    protected TypeParameterMatcher() {
    }
}
