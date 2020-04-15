package darkninja2462.purplematter.util.reflect;

import darkninja2462.purplematter.PurpleMatter;

import java.util.function.Supplier;

@FunctionalInterface
public interface ThrowingSupplier<T, E extends Throwable> extends Supplier<T> {
    @Override
    default T get() throws RuntimeException {
        try {
            return getThrows();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    T getThrows() throws E;
}
