package darkninja2462.purplematter.util.reflect;

import darkninja2462.purplematter.PurpleMatter;

import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> extends Consumer<T> {
    @Override
    default void accept(T t) throws RuntimeException {
        try {
            acceptThrows(t);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    void acceptThrows(T t) throws E;
}
