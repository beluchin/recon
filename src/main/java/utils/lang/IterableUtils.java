package utils.lang;

import java.util.ArrayList;

public class IterableUtils {
    public static <T> Iterable<T> empty() {
        return new ArrayList<>();
    }
}
