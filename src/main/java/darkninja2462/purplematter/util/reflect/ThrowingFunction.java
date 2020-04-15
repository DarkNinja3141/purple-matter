package darkninja2462.purplematter.util.reflect;

import darkninja2462.purplematter.PurpleMatter;

import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> extends Function<T, R> {
    @Override
    default R apply(T t) throws RuntimeException {
        try {
            return applyThrows(t);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    R applyThrows(T t) throws E;
}
