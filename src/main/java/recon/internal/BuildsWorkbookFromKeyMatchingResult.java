package recon.internal;

import recon.Workbook;
import recon.config.Key;
import recon.internal.datamodel.KeyMatchingResult;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorkbook;

import javax.inject.Inject;

class BuildsWorkbookFromKeyMatchingResult {
    private final BuildsWorkbook buildsWorkbook;
    private final BuildsDataWorksheet buildsDataWorksheet;

    @Inject
    BuildsWorkbookFromKeyMatchingResult(
            final BuildsWorkbook buildsWorkbook,
            final BuildsDataWorksheet buildsDataWorksheet) {
        this.buildsWorkbook = buildsWorkbook;
        this.buildsDataWorksheet = buildsDataWorksheet;
    }

    public Workbook build(
            final Key k,
            final KeyMatchingResult r,
            final String lhsName,
            final String rhsName) {
        final Worksheet s = buildsDataWorksheet.build(k, r, lhsName, rhsName);
        return buildsWorkbook.build(s);
    }
}
