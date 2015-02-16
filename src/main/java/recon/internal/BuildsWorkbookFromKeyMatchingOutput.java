package recon.internal;

import recon.Workbook;
import recon.config.Key;
import recon.internal.datamodel.KeyMatchingOutput;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorkbook;

import javax.inject.Inject;

class BuildsWorkbookFromKeyMatchingOutput {
    private final BuildsWorkbook buildsWorkbook;
    private final BuildsDataWorksheet buildsDataWorksheet;

    @Inject
    BuildsWorkbookFromKeyMatchingOutput(
            final BuildsWorkbook buildsWorkbook,
            final BuildsDataWorksheet buildsDataWorksheet) {
        this.buildsWorkbook = buildsWorkbook;
        this.buildsDataWorksheet = buildsDataWorksheet;
    }

    public Workbook build(Key k, final KeyMatchingOutput d) {
        Worksheet s = buildsDataWorksheet.build(k);
        return buildsWorkbook.build(s);
    }
}
