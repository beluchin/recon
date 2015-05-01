package recon.internal;

import com.google.common.collect.ImmutableMap;
import recon.config.Key;
import recon.internal.datamodel.KeyMatchingResult;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

import javax.inject.Inject;

class BuildsReconWorksheet {
    private final BuildsWorksheet buildsWorksheet;

    @Inject
    BuildsReconWorksheet(final BuildsWorksheet buildsWorksheet) {
        this.buildsWorksheet = buildsWorksheet;
    }

    public Worksheet build(final Key key, final KeyMatchingResult result) {
        return buildsWorksheet.build("recon", ImmutableMap.of());
    }

}
