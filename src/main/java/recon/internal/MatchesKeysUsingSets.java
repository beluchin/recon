package recon.internal;

import recon.Input;
import recon.internal.datamodel.KeyMatchingOutput;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Set;

import static com.google.common.collect.Sets.difference;
import static java.util.stream.Collectors.toSet;

class MatchesKeysUsingSets {
    private static class Key {
        private final Input.DataRow row;

        public Key(final Input.DataRow row) {
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

    private final BuildsKeyMatchingOutput buildsKeyMatchingOutput;

    @Inject
    MatchesKeysUsingSets(final BuildsKeyMatchingOutput buildsKeyMatchingOutput) {
        this.buildsKeyMatchingOutput = buildsKeyMatchingOutput;
    }

    public @Nullable KeyMatchingOutput matchKeys(final Input lhs, final Input rhs) {

        // the inputs may not have the columns in order.
        // no need to worry about that now. The inputs only have one column
        // for the current milestone.

        final Set<Key> lhsKeys = getKeys(lhs);
        final Set<Key> rhsKeys = getKeys(rhs);
        return existsPopulationBreaks(lhsKeys, rhsKeys)
                ? buildsKeyMatchingOutput.build()
                : null;
    }

    private Set<Key> getKeys(final Input input) {
        return input.getData().map(Key::new).collect(toSet());
    }

    private static boolean existsPopulationBreaks(
            final Set<Key> lhsKeys, final Set<Key> rhsKeys) {
        return difference(lhsKeys, rhsKeys).size() != 0
                || difference(rhsKeys, lhsKeys).size() != 0;
    }
}
