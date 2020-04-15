package darkninja2462.purplematter.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReflectionUtils {

    public static <T> Consumer<T> wrap(ThrowingConsumer<T, ReflectiveOperationException> f) throws RuntimeException {
        return f;
    }

    public static <T, R> Function<T, R> wrap(ThrowingFunction<T, R, ReflectiveOperationException> f) throws RuntimeException {
        return f;
    }

    public static Runnable wrap(ThrowingRunnable<ReflectiveOperationException> f) throws RuntimeException {
        return f;
    }

    public static <T> Supplier<T> wrap(ThrowingSupplier<T, ReflectiveOperationException> f) throws RuntimeException {
        return f;
    }

    public static Object getPrivateField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        do {
            try {
                Field f = clazz.getDeclaredField(name);
                f.setAccessible(true);
                return f.get(obj);
            } catch (NoSuchFieldException ignored) {}
        } while((clazz = clazz.getSuperclass()) != null);
        throw new NoSuchFieldException(name);
    }

    public static Object getPrivateField(Class<?> clazz, String name) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(name);
        f.setAccessible(true);
        return f.get(null);
    }

    public static Object callPrivateMethod(Object obj, String name, Class<?>[] parameterTypes, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = obj.getClass().getDeclaredMethod(name, parameterTypes);
        m.setAccessible(true);
        return m.invoke(obj, args);
    }

    public static Object callPrivateMethod(Class<?> clazz, String name, Class<?>[] parameterTypes, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = clazz.getDeclaredMethod(name, parameterTypes);
        m.setAccessible(true);
        return m.invoke(null, args);
    }

}
