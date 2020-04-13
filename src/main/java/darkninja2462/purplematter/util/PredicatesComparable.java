package darkninja2462.purplematter.util;

import java.util.function.Predicate;

public class PredicatesComparable {

    public static <T extends Comparable<T>> Predicate<T> isEqual(T targetRef) {
        return object -> object.compareTo(targetRef) == 0;
    }

    public static <T extends Comparable<T>> Predicate<T> isNotEqual(T targetRef) {
        return object -> object.compareTo(targetRef) != 0;
    }

    public static <T extends Comparable<T>> Predicate<T> isGreaterThan(T targetRef) {
        return object -> object.compareTo(targetRef) > 0;
    }

    public static <T extends Comparable<T>> Predicate<T> isGreaterThanOrEqualTo(T targetRef) {
        return object -> object.compareTo(targetRef) >= 0;
    }

    public static <T extends Comparable<T>> Predicate<T> isLessThan(T targetRef) {
        return object -> object.compareTo(targetRef) < 0;
    }

    public static <T extends Comparable<T>> Predicate<T> isLessThanOrEqualTo(T targetRef) {
        return object -> object.compareTo(targetRef) <= 0;
    }

}
