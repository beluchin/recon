package recon.internal;

import recon.BuildsWorkbookFromInputs;
import recon.Input;
import recon.Workbook;
import recon.config.Key;
import recon.internal.datamodel.KeyMatchingOutput;

import javax.annotation.Nullable;
import javax.inject.Inject;

class BuildsWorkbookFromInputsImpl implements BuildsWorkbookFromInputs {
    private final BuildsWorkbookFromKeyMatchingOutput buildsWorkbook;
    private final MatchesKeysUsingSets matchesKeys;
    private ProvidesKey providesKey;

    @Inject
    BuildsWorkbookFromInputsImpl(
            final BuildsWorkbookFromKeyMatchingOutput buildsWorkbook,
            final MatchesKeysUsingSets matchesKeys,
            final ProvidesKey providesKey) {
        this.buildsWorkbook = buildsWorkbook;
        this.matchesKeys = matchesKeys;
        this.providesKey = providesKey;
    }

    public @Nullable
    Workbook recon(final Input lhs, final Input rhs) {
        final Key k = providesKey.get(lhs, rhs);
        final KeyMatchingOutput d = matchesKeys.matchKeys(lhs, rhs);
        return d != null
                ? buildsWorkbook.build(k, d, lhs.getName(), rhs.getName())
                : null;
    }
}
