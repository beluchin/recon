package recon.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import recon.Input;
import recon.internal.datamodel.KeyMatchingOutput;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.difference;
import static java.util.stream.Collectors.toSet;

class MatchesKeysUsingSets {
    private static class Key {
        private final List<String> strings;

        public Key(final List<String> strings) {
            this.strings = strings;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Key key = (Key) o;
            return strings.equals(key.strings);
        }

        @Override
        public int hashCode() {
            return strings.hashCode();
        }
    }

    private final BuildsKeyMatchingOutput buildsKeyMatchingOutput;

    @Inject
    MatchesKeysUsingSets(final BuildsKeyMatchingOutput buildsKeyMatchingOutput) {
        this.buildsKeyMatchingOutput = buildsKeyMatchingOutput;
    }

    public @Nullable KeyMatchingOutput matchKeys(final Input rhs, final Input lhs) {
        final Set<Integer> keyDefinition = getKeyDefinition(lhs, rhs);
        final Set<Key> lhsKeys = getKeys(lhs, keyDefinition);
        final Set<Key> rhsKeys = getKeys(rhs, keyDefinition);
        return existsPopulationBreaks(lhsKeys, rhsKeys)
                ? buildsKeyMatchingOutput.build()
                : null;
    }

    @SuppressWarnings("UnusedParameters")
    private ImmutableSet<Integer> getKeyDefinition(final Input lhs, final Input rhs) {
        return ImmutableSet.of(0);
    }

    private Set<Key> getKeys(
            final Input input, final Set<Integer> keyDefinition) {
        return input.getData().map(r -> getKey(r, keyDefinition))
                .collect(toSet());
    }

    private Key getKey(final Input.DataRow r, final Set<Integer> keyDefinition) {
        final ImmutableList.Builder<String> builder = new ImmutableList.Builder<>();
        final List<String> strings = r.get();
        for (final int index: keyDefinition) {
            builder.add(strings.get(index));
        }
        return new Key(builder.build());
    }

    private static boolean existsPopulationBreaks(
            final Set<Key> lhsKeys, final Set<Key> rhsKeys) {
        return difference(lhsKeys, rhsKeys).size() != 0
                || difference(rhsKeys, lhsKeys).size() != 0;
    }
}
