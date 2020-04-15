package darkninja2462.purplematter.util;

import java.util.Collection;
import java.util.function.*;

public class Suppliers {

    public static <T> Supplier<T> constant(T value) {
        return () -> value;
    }

    public static BooleanSupplier constant(boolean value) {
        return () -> value;
    }

    public static DoubleSupplier constant(double value) {
        return () -> value;
    }

    public static IntSupplier constant(int value) {
        return () -> value;
    }

    public static LongSupplier constant(long value) {
        return () -> value;
    }

    public static BooleanSupplier or(Collection<BooleanSupplier> suppliers) {
        return () -> suppliers.stream().map(BooleanSupplier::getAsBoolean).reduce(false, Boolean::logicalOr);
    }

}
