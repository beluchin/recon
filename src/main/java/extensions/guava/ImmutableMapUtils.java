package extensions.guava;

import com.google.common.collect.ImmutableMap.Builder;

import java.util.Map;
import java.util.stream.Stream;

public class ImmutableMapUtils {
    /**
     * missing on the ImmutableMap interface on Guava 18
     */
    public static <K, V, E extends Map.Entry<K, V>> Map<K, V> copyOf(
            final Stream<E> entries) {
        Builder<K, V> builder = new Builder<>();
        entries.forEach(builder::put);
        return builder.build();
    }
}
