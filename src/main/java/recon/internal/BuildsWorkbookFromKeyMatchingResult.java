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
    private BuildsReconWorksheet buildsReconWorksheet;

    @Inject
    BuildsWorkbookFromKeyMatchingResult(
            final BuildsWorkbook buildsWorkbook,
            final BuildsDataWorksheet buildsDataWorksheet,
            final BuildsReconWorksheet buildsReconWorksheet) {
        this.buildsWorkbook = buildsWorkbook;
        this.buildsDataWorksheet = buildsDataWorksheet;
        this.buildsReconWorksheet = buildsReconWorksheet;
    }

    public Workbook build(
            final Key k,
            final KeyMatchingResult r,
            final String lhsName,
            final String rhsName) {
        final Worksheet data = buildsDataWorksheet.build(k, r, lhsName, rhsName);
        final Worksheet recon = buildsReconWorksheet.build(k, r);
        return buildsWorkbook.build(recon, data);
    }
}
