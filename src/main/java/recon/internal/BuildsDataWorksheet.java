package recon.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import recon.config.Key;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

import javax.inject.Inject;
import java.util.List;

class BuildsDataWorksheet {
    private BuildsWorksheet buildsWorksheet;

    @Inject
    BuildsDataWorksheet(final BuildsWorksheet buildsWorksheet) {
        this.buildsWorksheet = buildsWorksheet;
    }

    public Worksheet build(final Key k) {
        return buildsWorksheet.build(
                "data", ImmutableMap.of(0, getColumnNames(k)));
    }

    private static List<String> getColumnNames(final Key k) {
        return new ImmutableList.Builder<String>()
                .addAll(k.asList())
                .add("~InputName~", "~RecordType~")
                .build();
    }
}
