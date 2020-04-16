package darkninja2462.purplematter.util;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

    private enum MatchType {
        EQUAL("="),
        NOT_EQUAL("!="),
        GREATER_THAN(">"),
        GREATER_THAN_OR_EQUAL(">="),
        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL("<=");

        private String key;
        MatchType(String key) {
            this.key = key;
        }

        public static Optional<MatchType> of(String key) {
            return Arrays.stream(MatchType.values()).filter((e) -> e.key.equals(key)).findFirst();
        }
    }

    public static <T extends Comparable<T>> Function<T, Predicate<T>> of(String matchType) throws IllegalArgumentException {
        Optional<MatchType> type = MatchType.of(matchType);
        if(!type.isPresent()) throw new IllegalArgumentException("Unknown match type: " + matchType);
        switch(type.get()) {
            case EQUAL:
                return PredicatesComparable::isEqual;
            case NOT_EQUAL:
                return PredicatesComparable::isNotEqual;
            case GREATER_THAN:
                return PredicatesComparable::isGreaterThan;
            case GREATER_THAN_OR_EQUAL:
                return PredicatesComparable::isGreaterThanOrEqualTo;
            case LESS_THAN:
                return PredicatesComparable::isLessThan;
            case LESS_THAN_OR_EQUAL:
                return PredicatesComparable::isLessThanOrEqualTo;
            default:
                throw new IllegalArgumentException("Unknown match type: " + matchType);
        }
    }

}
