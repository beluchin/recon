package recon.internal;

import com.google.common.collect.ImmutableList;
import recon.Workbook;
import recon.config.Key;
import recon.internal.datamodel.KeyMatchingOutput;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorkbook;
import recon.internal.deps.BuildsWorksheet;

import javax.inject.Inject;
import java.util.List;

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

    public Workbook build(Key k, final KeyMatchingOutput d) {
        Worksheet s = buildsWorksheet
                .row(0, getColumnNames(k))
                .build("data");
        return buildsWorkbook.build(s);
    }

    private static List<String> getColumnNames(final Key k) {
        return new ImmutableList.Builder<String>()
                .addAll(k.asList())
                .add("~InputName~", "~RecordType~")
                .build();
    }
}
