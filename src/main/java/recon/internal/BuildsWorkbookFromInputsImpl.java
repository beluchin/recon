package recon.internal;

import recon.BuildsWorkbookFromInputs;
import recon.Workbook;
import recon.Input;
import recon.internal.datamodel.KeyMatchingOutput;

import javax.annotation.Nullable;
import javax.inject.Inject;

class BuildsWorkbookFromInputsImpl implements BuildsWorkbookFromInputs {
    private final BuildsWorkbookFromKeyMatchingOutput buildsWorkbook;
    private final MatchesKeysUsingSets matchesKeys;

    @Inject
    BuildsWorkbookFromInputsImpl(
            final BuildsWorkbookFromKeyMatchingOutput buildsWorkbook,
            final MatchesKeysUsingSets matchesKeys) {
        this.buildsWorkbook = buildsWorkbook;
        this.matchesKeys = matchesKeys;
    }

    public @Nullable
    Workbook recon(final Input lhs, final Input rhs) {
        final KeyMatchingOutput d = matchesKeys.matchKeys(lhs, rhs);
        return d != null? buildsWorkbook.build(d) : null;
    }
}
