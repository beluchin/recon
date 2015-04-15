package recon.internal;

import recon.BuildsWorkbookFromInputs;
import recon.Input;
import recon.Workbook;
import recon.config.Key;
import recon.internal.datamodel.KeyMatchingResult;

import javax.inject.Inject;
import java.util.Optional;

class BuildsWorkbookFromInputsImpl implements BuildsWorkbookFromInputs {
    private final BuildsWorkbookFromKeyMatchingResult buildsWorkbook;
    private final MatchesKeysUsingSets matchesKeys;
    private final ProvidesKey providesKey;

    @Inject
    BuildsWorkbookFromInputsImpl(
            final BuildsWorkbookFromKeyMatchingResult buildsWorkbook,
            final MatchesKeysUsingSets matchesKeys,
            final ProvidesKey providesKey) {
        this.buildsWorkbook = buildsWorkbook;
        this.matchesKeys = matchesKeys;
        this.providesKey = providesKey;
    }

    public Optional<Workbook> recon(final Input lhs, final Input rhs) {
        final Key k = providesKey.get(lhs, rhs);
        final KeyMatchingResult d = matchesKeys.matchKeys(lhs, rhs);
        return existsIssues(d) || existsDataColumns(lhs, rhs)
                ? Optional.of(buildsWorkbook.build(k, d, lhs.getName(), rhs.getName()))
                : Optional.empty();
    }

    @SuppressWarnings("UnusedParameters")
    private boolean existsDataColumns(final Input lhs, final Input rhs) {
        return false;
    }

    private boolean existsIssues(final KeyMatchingResult d) {
        return existsPopulationBreaks(d);
    }

    private boolean existsPopulationBreaks(final KeyMatchingResult d) {
        return !d.populationBreaks.getLeft().isEmpty()
                || !d.populationBreaks.getRight().isEmpty();
    }
}
