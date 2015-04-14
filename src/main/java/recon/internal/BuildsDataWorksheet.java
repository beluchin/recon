package recon.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import extensions.guava.ImmutableMapUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import recon.Input.DataRow;
import recon.config.Key;
import recon.internal.datamodel.KeyMatchingResult;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

class BuildsDataWorksheet {
    private BuildsWorksheet buildsWorksheet;

    @Inject
    BuildsDataWorksheet(final BuildsWorksheet buildsWorksheet) {
        this.buildsWorksheet = buildsWorksheet;
    }

    public Worksheet build(
            final Key key,
            final KeyMatchingResult result,
            final String lhsName,
            final String rhsName) {
        class Helper {
            private int rowNumber = 0;

            public Builder<Integer, List<String>> rowBuilder() {
                return new Builder<>();
            }

            public Map<Integer, List<String>> reconRows() {
                final Stream<ImmutablePair<Integer, List<String>>> entries = result.reconRows
                        .stream()
                        .flatMap(p -> Stream.of(
                                ImmutablePair.of(rowNumber++, buildReconRow(p.getLeft(), lhsName)),
                                ImmutablePair.of(rowNumber++, buildReconRow(p.getRight(), rhsName))));
                return ImmutableMapUtils.copyOf(entries);
            }

            public Map<Integer, List<String>> populationBreaks() {
                return ImmutableMapUtils.copyOf(Stream.concat(
                        getLhsPopulationBreakRows(),
                        getRhsPopulationBreakRows()));
            }

            public Entry<Integer, List<String>> columnNameRow() {
                return ImmutablePair.of(rowNumber++, new ImmutableList.Builder<String>()
                        .addAll(key.asList())
                        .add("~InputName~", "~RecordType~")
                        .build());
            }

            private Stream<ImmutablePair<Integer, List<String>>> getRhsPopulationBreakRows() {
                return result.populationBreaks.getRight().stream()
                        .map(r -> ImmutablePair.of(rowNumber++, buildPopulationBreakRow(r, rhsName)));
            }

            private Stream<ImmutablePair<Integer, List<String>>> getLhsPopulationBreakRows() {
                return result.populationBreaks.getLeft().stream()
                        .map(r -> ImmutablePair.of(rowNumber++, buildPopulationBreakRow(r, lhsName)));
            }
        }

        final Helper h = new Helper();
        return buildsWorksheet.build(
                worksheetName("data"),
                h.rowBuilder()
                        .put(h.columnNameRow())
                        .putAll(h.reconRows())
                        .putAll(h.populationBreaks())
                        .build());
    }

    private static List<String> buildPopulationBreakRow(final DataRow r, final String inputName) {
        return buildRow(r, inputName, "missing");
    }

    private static List<String> buildReconRow(final DataRow row, final String inputName) {
        return buildRow(row, inputName, "common");
    }

    private static List<String> buildRow(final DataRow row, final String inputName, final String recordType) {
        return new ImmutableList.Builder<String>()
                .addAll(row.get())
                .add(inputName)
                .add(recordType)
                .build();
    }

    private static String worksheetName(final String s) {
        return s;
    }

}
