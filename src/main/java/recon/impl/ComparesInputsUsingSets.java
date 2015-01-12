package recon.impl;

import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;
import recon.ComparesInputs;
import recon.datamodel.ExcelWorkbook;
import recon.datamodel.Input;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.difference;
import static java.util.stream.Collectors.toSet;

class ComparesInputsUsingSets implements ComparesInputs {
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

    private final Provider<ExcelWorkbook> workbookProvider;

    @Inject
    ComparesInputsUsingSets(final Provider<ExcelWorkbook> workbookProvider) {
        this.workbookProvider = workbookProvider;
    }

    public @Nullable ExcelWorkbook recon(final Input rhs, final Input lhs) {
        Set<Integer> keyDefinition = getKeyDefinition(lhs, rhs);
        Set<Key> lhsKeys = getKeys(lhs, keyDefinition);
        Set<Key> rhsKeys = getKeys(rhs, keyDefinition);
        if (existsPopulationBreaks(lhsKeys, rhsKeys)) {
            return workbookProvider.get();
        }
        return null;
    }

    private ImmutableSet<Integer> getKeyDefinition(final Input lhs, final Input rhs) {
        return ImmutableSet.of(0);
    }

    private Set<Key> getKeys(final Input input, final Set<Integer> keyDefinition) {
        return input.getData().map(r -> getKey(r, keyDefinition))
                .collect(toSet());
    }

    private Key getKey(final Input.DataRow r, final Set<Integer> keyDefinition) {
        Builder<String> builder = new Builder<>();
        for (int index: keyDefinition) {
            builder.add(r.get(index));
        }
        return new Key(builder.build());
    }

    private static boolean existsPopulationBreaks(
            final Set<Key> lhsKeys, final Set<Key> rhsKeys) {
        return difference(lhsKeys, rhsKeys).size() != 0
                || difference(rhsKeys, lhsKeys).size() != 0;
    }
}
