package recon.internal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import recon.Input;
import recon.Input.DataRow;
import recon.internal.datamodel.KeyMatchingOutput;
import recon.internal.datamodel.KeyMatchingOutput.Builder;

import javax.annotation.Nullable;
import java.util.ArrayList;
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

    public @Nullable KeyMatchingOutput matchKeys(final Input lhs, final Input rhs) {

        // the inputs may not have the columns in order.
        // no need to worry about that now. The inputs only have one column
        // for the current milestone.

        final Map<Key, DataRow> lhsKeysAndRows = getKeys(lhs);
        final Map<Key, DataRow> rhsKeysAndRows = getKeys(rhs);
        final Set<Key> rhsKeys = rhsKeysAndRows.keySet();
        final Set<Key> lhsKeys = lhsKeysAndRows.keySet();
        final Builder builder = new Builder();
        final Set<Key> shared = intersection(lhsKeys, rhsKeys);
        if (shared.size() != 0) {
            builder.reconRows(getReconRows(
                    lhsKeysAndRows, rhsKeysAndRows, shared));
        }
        return existsPopulationBreaks(lhsKeys, rhsKeys)
                ? builder.build()
                : null;
    }

    private List<ImmutablePair<DataRow, DataRow>> getReconRows(
            final Map<Key, DataRow> lhsKeysAndRows,
            final Map<Key, DataRow> rhsKeysAndRows,
            final Set<Key> shared) {
        final List<ImmutablePair<DataRow, DataRow>> result = new ArrayList<>();
        shared.forEach(k -> result.add(ImmutablePair.of(
                lhsKeysAndRows.get(k), rhsKeysAndRows.get(k))));
        return result;
    }

    private Map<Key, DataRow> getKeys(final Input input) {
        final Map<Key, DataRow> result = new HashMap<>();
        input.getData().forEach(r -> result.put(new Key(r), r));
        return result;
    }

    private static boolean existsPopulationBreaks(
            final Set<Key> lhsKeys, final Set<Key> rhsKeys) {
        return difference(lhsKeys, rhsKeys).size() != 0
                || difference(rhsKeys, lhsKeys).size() != 0;
    }
}
