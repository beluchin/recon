package recon.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import recon.Input;
import recon.Input.DataRow;
import recon.internal.datamodel.KeyMatchingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.intersection;

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

    private static class MatchKeysImpl {
        private final Map<Key, DataRow> lhsKeysAndRows;
        private final Map<Key, DataRow> rhsKeysAndRows;
        private final Set<Key> lhsKeys;
        private final Set<Key> rhsKeys;

        public MatchKeysImpl(
                final Map<Key, DataRow> lhsKeysAndRows,
                final Map<Key, DataRow> rhsKeysAndRows) {
            this.lhsKeysAndRows = lhsKeysAndRows;
            this.rhsKeysAndRows = rhsKeysAndRows;
            lhsKeys = lhsKeysAndRows.keySet();
            rhsKeys = rhsKeysAndRows.keySet();
        }

        public List<Pair<DataRow,DataRow>> getReconRows() {
            final Set<Key> shared = intersection(lhsKeys, rhsKeys);
            if (shared.size() == 0) {
                return ImmutableList.of();
            }
            final Builder<Pair<DataRow, DataRow>> builder = new Builder<>();
            shared.forEach(k -> builder.add(ImmutablePair.of(
                    lhsKeysAndRows.get(k), rhsKeysAndRows.get(k))));
            return builder.build();
        }

        public Pair<List<DataRow>, List<DataRow>> getPopulationBreaks() {
            return ImmutablePair.of(missingOnRhs(), missingOnLhs());
        }

        private List<DataRow> missingOnRhs() {
            final Set<Key> missingKeys = difference(lhsKeys, rhsKeys);
            final Builder<DataRow> builder = new Builder<>();
            missingKeys.forEach(k -> builder.add(lhsKeysAndRows.get(k)));
            return builder.build();
        }

        private List<DataRow> missingOnLhs() {
            final Set<Key> missingKeys = difference(rhsKeys, lhsKeys);
            final Builder<DataRow> builder = new Builder<>();
            missingKeys.forEach(k -> builder.add(rhsKeysAndRows.get(k)));
            return builder.build();
        }
    }

    public KeyMatchingResult matchKeys(final Input lhs, final Input rhs) {

        // method implemented with object to make it easy to handle invocation state

        final MatchKeysImpl impl = buildMatchesKeyImpl(lhs, rhs);
        return new KeyMatchingResult(
                impl.getReconRows(),
                impl.getPopulationBreaks());
    }

    private MatchKeysImpl buildMatchesKeyImpl(final Input lhs, final Input rhs) {
        final Map<Key, DataRow> lhsKeysAndRows = getKeys(lhs);
        final Map<Key, DataRow> rhsKeysAndRows = getKeys(rhs);
        return new MatchKeysImpl(lhsKeysAndRows, rhsKeysAndRows);
    }

    private Map<Key, DataRow> getKeys(final Input input) {
        final Map<Key, DataRow> result = new HashMap<>();
        input.getData().forEach(r -> result.put(new Key(r), r));
        return result;
    }

}
