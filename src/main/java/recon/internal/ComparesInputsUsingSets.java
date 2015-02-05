package recon.internal;

import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;
import recon.ComparesInputs;
import recon.ExcelWorkbook;
import recon.Input;
import recon.internal.deps.BuildsExcelWorkbook;

import javax.annotation.Nullable;
import javax.inject.Inject;
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

    private final BuildsExcelWorkbook buildsWorkbook;

    @Inject
    ComparesInputsUsingSets(final BuildsExcelWorkbook buildsWorkbook) {
        this.buildsWorkbook = buildsWorkbook;
    }

    public @Nullable
    ExcelWorkbook recon(final Input rhs, final Input lhs) {
        final Set<Integer> keyDefinition = getKeyDefinition(lhs, rhs);
        final Set<Key> lhsKeys = getKeys(lhs, keyDefinition);
        final Set<Key> rhsKeys = getKeys(rhs, keyDefinition);
        if (existsPopulationBreaks(lhsKeys, rhsKeys)) {
            return buildWorkbook();
        }
        return null;
    }

    private ExcelWorkbook buildWorkbook() {
        final ExcelWorkbook result = buildsWorkbook.build();
        result.addSheet("data");
        return result;
    }

    private ImmutableSet<Integer> getKeyDefinition(
            final Input lhs, final Input rhs) {
        return ImmutableSet.of(0);
    }

    private Set<Key> getKeys(
            final Input input, final Set<Integer> keyDefinition) {
        return input.getData().map(r -> getKey(r, keyDefinition))
                .collect(toSet());
    }

    private Key getKey(final Input.DataRow r, final Set<Integer> keyDefinition) {
        final Builder<String> builder = new Builder<>();
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
