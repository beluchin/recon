package recon.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import recon.Input;
import recon.Input.DataRow;
import recon.internal.datamodel.KeyMatchingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.intersection;
import static java.util.stream.Collectors.toList;

class MatchesKeysUsingSets {

    private static class Key {
        private final DataRow row;

        public Key(final DataRow row) {
            this.row = row;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Key key = (Key) o;
            return getToken(0).equals(key.getToken(0));
        }

        @Override
        public int hashCode() {
            return getToken(0).hashCode();
        }

        private String getToken(final int index) {
            return row.get().get(index);
        }
    }

    public KeyMatchingResult matchKeys(final Input lhs, final Input rhs) {

        class Helper {
            private final List<Map.Entry<Key, DataRow>> lhsAllKeysAndRows =
                    getAllKeysAndRows(lhs);
            private final List<Map.Entry<Key, DataRow>> rhsAllKeysAndRows =
                    getAllKeysAndRows(rhs);
            private final Map<Key, DataRow> lhsDistinctKeysAndRows =
                    getDistinctRows(lhsAllKeysAndRows.stream());
            private final Map<Key, DataRow> rhsDistinctKeysAndRows =
                    getDistinctRows(rhsAllKeysAndRows.stream());
            private final Set<Key> lhsDistinctKeys = lhsDistinctKeysAndRows.keySet();
            private final Set<Key> rhsDistinctKeys = rhsDistinctKeysAndRows.keySet();

            public List<Pair<DataRow,DataRow>> getReconRows() {
                final Set<Key> shared = intersection(lhsDistinctKeys, rhsDistinctKeys);
                if (shared.size() == 0) {
                    return ImmutableList.of();
                }
                final Builder<Pair<DataRow, DataRow>> builder = new Builder<>();
                shared.forEach(k -> builder.add(ImmutablePair.of(
                        lhsDistinctKeysAndRows.get(k), rhsDistinctKeysAndRows.get(k))));
                return builder.build();
            }

            public Pair<List<DataRow>, List<DataRow>> getPopulationBreaks() {
                return ImmutablePair.of(missingOnRhs(), missingOnLhs());
            }

            public Pair<List<DataRow>, List<DataRow>> getDuplicates() {
                return ImmutablePair.of(duplicatesOnLhs(), duplicatesOnRhs());
            }

            private List<Map.Entry<Key, DataRow>> getAllKeysAndRows(final Input input) {
                return input.getData()
                        .map(r -> ImmutablePair.of(new Key(r), r))
                        .collect(toList());
            }

            private List<DataRow> duplicatesOnRhs() {
                return getDuplicateRows(rhsAllKeysAndRows.stream());
            }

            private List<DataRow> duplicatesOnLhs() {
                return getDuplicateRows(lhsAllKeysAndRows.stream());
            }

            private List<DataRow> missingOnRhs() {
                final Set<Key> missingKeys = difference(lhsDistinctKeys, rhsDistinctKeys);
                final Builder<DataRow> builder = new Builder<>();
                missingKeys.forEach(k -> builder.add(lhsDistinctKeysAndRows.get(k)));
                return builder.build();
            }

            private List<DataRow> missingOnLhs() {
                final Set<Key> missingKeys = difference(rhsDistinctKeys, lhsDistinctKeys);
                final Builder<DataRow> builder = new Builder<>();
                missingKeys.forEach(k -> builder.add(rhsDistinctKeysAndRows.get(k)));
                return builder.build();
            }
        }

        final Helper h = new Helper();
        return new KeyMatchingResult(
                h.getReconRows(),
                h.getPopulationBreaks(),
                h.getDuplicates());
    }

    private Map<Key, DataRow> getDistinctRows(
            final Stream<Map.Entry<Key, DataRow>> keysAndRows) {
        final Map<Key, DataRow> result = new HashMap<>();
        keysAndRows.reduce(
                result,
                (m, entry) -> {
                    if (m.containsKey(entry.getKey())) {
                        m.remove(entry.getKey());
                    }
                    else {
                        put(m, entry);
                    }
                    return m;
                },
                (m1, m2) -> result);

        return result;
    }

    private List<DataRow> getDuplicateRows(
            final Stream<Map.Entry<Key, DataRow>> keysAndRows) {
        final List<DataRow> result = new ArrayList<>();
        final Set<Key> duplicates = new HashSet<>();
        final Map<Key, DataRow> firstEntries = new HashMap<>();

        keysAndRows.forEach(e -> {
            final Key k = e.getKey();
            final DataRow r = e.getValue();
            if (firstEntries.containsKey(k)) {
                duplicates.add(k);
                result.add(r);
            }
            else {
                firstEntries.put(k, r);
            }
        });

        duplicates.stream().forEach(k -> result.add(firstEntries.get(k)));

        return result;
    }

    private static void put(
            final Map<Key, DataRow> map, final Map.Entry<Key, DataRow> entry) {
        map.put(entry.getKey(), entry.getValue());
    }

}
