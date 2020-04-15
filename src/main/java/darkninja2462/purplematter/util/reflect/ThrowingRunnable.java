package darkninja2462.purplematter.util.reflect;

import darkninja2462.purplematter.PurpleMatter;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingRunnable<E extends Throwable> extends Runnable {
    @Override
    default void run() throws RuntimeException {
        try {
            acceptRun();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    void acceptRun() throws E;
}
