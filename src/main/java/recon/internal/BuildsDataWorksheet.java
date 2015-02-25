package recon.internal;

import com.google.common.collect.ImmutableBiMap.Builder;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import recon.Input.DataRow;
import recon.config.Key;
import recon.internal.datamodel.KeyMatchingOutput;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BuildsDataWorksheet {
    private BuildsWorksheet buildsWorksheet;

    @Inject
    BuildsDataWorksheet(final BuildsWorksheet buildsWorksheet) {
        this.buildsWorksheet = buildsWorksheet;
    }

    public Worksheet build(
            final Key k,
            final KeyMatchingOutput d,
            final String lhsName,
            final String rhsName) {
        final Builder<Integer, List<String>> rowsBuilder = new Builder<>();
        rowsBuilder.put(0, getColumnNames(k));
        rowsBuilder.putAll(getReconRows(
                d, lhsName, rhsName, startingAtRow(1)));
        return buildsWorksheet.build(
                "data",
                rowsBuilder.build());
    }

    private static List<String> getColumnNames(final Key k) {
        return new ImmutableList.Builder<String>()
                .addAll(k.asList())
                .add("~InputName~", "~RecordType~")
                .build();
    }

    private static List<String> buildReconRow(final DataRow row, final String inputName) {
        return new ImmutableList.Builder<String>()
                .addAll(row.get())
                .add(inputName)
                .add("common")
                .build();
    }

    private static Map<Integer, List<String>> getReconRows(
            final KeyMatchingOutput d,
            final String lhsName,
            final String rhsName,
            final int startingAt) {
        final Map<Integer, List<String>> result = new HashMap<>();
        int row = startingAt;
        for (Pair<DataRow, DataRow> p: d.reconRows) {
            result.put(row, buildReconRow(p.getLeft(), lhsName));
            result.put(row + 1, buildReconRow(p.getRight(), rhsName));
        }
        return result;
    }

    private static int startingAtRow(final int i) {
        return i;
    }
}
