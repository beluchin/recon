package recon.internal;

import recon.Workbook;
import recon.internal.datamodel.KeyMatchingOutput;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorkbook;
import recon.internal.deps.BuildsWorksheet;

import javax.inject.Inject;

class BuildsWorkbookFromKeyMatchingOutput {
    private BuildsWorkbook buildsWorkbook;
    private BuildsWorksheet buildsWorksheet;

    @Inject
    BuildsWorkbookFromKeyMatchingOutput(
            final BuildsWorkbook buildsWorkbook,
            final BuildsWorksheet buildsWorksheet) {
        this.buildsWorkbook = buildsWorkbook;
        this.buildsWorksheet = buildsWorksheet;
    }

    public Workbook build(final KeyMatchingOutput d) {
        Worksheet s = buildsWorksheet.build("data");
        return buildsWorkbook.build(s);
    }
}
