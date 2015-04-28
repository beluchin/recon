package bdd.tests;

import bdd.AbstractBddTest;
import bdd.datamodel.BddWorkbook;
import bdd.datamodel.BddWorksheet;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import recon.BuildsWorkbookFromInputs;
import recon.Input;
import recon.Workbook;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static bdd.datamodel.InputUtils.data;
import static bdd.datamodel.InputUtils.dataRow;
import static bdd.datamodel.InputUtils.schema;
import static bdd.datamodel.InputUtils.toInput;
import static com.google.common.base.Stopwatch.createStarted;
import static java.util.Collections.shuffle;
import static java.util.UUID.randomUUID;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.generate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SingleColumnKeyNoDataTest extends AbstractBddTest {

    private BuildsWorkbookFromInputs buildsWorkbookFromInputs;
    private static final long _50K = 50000;
    private static final long _25K = 25000;

    @Test
    public void _1_noOutputIfInputsAreIdentical() {
        final Input input = toInput(
                schema("Column1"),
                dataRow("Hello"));
        final Optional<Workbook> result = buildsWorkbookFromInputs.recon(input, input);
        assertFalse(result.isPresent());
    }

    @Test
    public void _2_outputIfInputsAreNotIdentical() {
        final Input lhs = toInput(
                schema("Column1"),
                dataRow("Hello"));
        final Input rhs = toInput(
                schema("Column1"),
                dataRow("World"));
        final Optional<Workbook> result = buildsWorkbookFromInputs.recon(lhs, rhs);
        assertTrue(result.isPresent());
    }

    @Test
    public void _3_workbookHasDataWorksheet() {
        final Input lhs = toInput(
                schema("Column1"),
                dataRow("Hello"));
        final Input rhs = toInput(
                schema("Column1"),
                dataRow("World"));

        final BddWorkbook result = (BddWorkbook) buildsWorkbookFromInputs.recon(lhs, rhs).get();

        assertThat(result.getSheet("data"), is(not(nullValue())));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void _4_columnsOnDataWorksheet() {
        final Input lhs = toInput(
                schema("Column1"),
                dataRow("Hello"));
        final Input rhs = toInput(
                schema("Column1"),
                dataRow("World"));

        final BddWorksheet worksheet = getWorksheet("data", lhs, rhs);

        assertThat(worksheet.getRow(0), is(ImmutableList.of(
                "Column1", "~InputName~", "~RecordType~")));
    }

    @Test
    public void _5_commonRecordsOnDataSheet() {
        final Input lhs = toInput(
                "LHS",
                schema("Column1"),
                dataRow("Hello"));
        final Input rhs = toInput(
                "RHS",
                schema("Column1"),
                dataRow("Hello"),
                dataRow("World"));

        final BddWorksheet worksheet = getWorksheet("data", lhs, rhs);

        assertThat(worksheet.getRow(1), is(ImmutableList.of(
                "Hello", "LHS", "common")));
        assertThat(worksheet.getRow(2), is(ImmutableList.of(
                "Hello", "RHS", "common")));
    }

    @Test
    public void _6_populationBreakRecordsOnDataSheet() {
        final Input lhs = toInput(
                "LHS",
                schema("Column1"),
                dataRow("Hello"));
        final Input rhs = toInput(
                "RHS",
                schema("Column1")); // no data

        final BddWorksheet worksheet = getWorksheet("data", lhs, rhs);

        assertThat(worksheet.getRow(1), is(ImmutableList.of(
                "Hello", "LHS", "missing")));
    }

    @Test
    public void _7_duplicateRecordsOnDataSheet() {
        final Input lhs = toInput(
                "LHS",
                schema("Column1"),
                dataRow("Hello"),
                dataRow("Hello"));
        final Input rhs = toInput(
                "RHS",
                schema("Column1")); // no data

        final BddWorksheet worksheet = getWorksheet("data", lhs, rhs);

        assertThat(worksheet.getRow(1), is(ImmutableList.of(
                "Hello", "LHS", "duplicate")));
        assertThat(worksheet.getRow(2), is(ImmutableList.of(
                "Hello", "LHS", "duplicate")));
    }

    @Test
    public void _8_reconSheet() {
        final Input lhs = toInput(
                schema("Column1"),
                dataRow("Hello"));
        final Input rhs = toInput(
                schema("Column1"),
                dataRow("World"));

        final BddWorkbook result = (BddWorkbook) buildsWorkbookFromInputs.recon(lhs, rhs).get();

        assertThat(result.getSheet("recon"), is(not(nullValue())));
    }

    @Test
    public void rhsPopulationBreakRecordsOnDataSheet() {
        final Input lhs = toInput(
                "LHS",
                schema("Column1"));
        final Input rhs = toInput(
                "RHS",
                schema("Column1"),
                dataRow("Hello"));

        final BddWorksheet worksheet = getWorksheet("data", lhs, rhs);

        assertThat(worksheet.getRow(1), is(ImmutableList.of(
                "Hello", "RHS", "missing")));
    }

    @Test
    public void performanceNoBreaks() {
        final Stream<String> uniqueStrings = randomStrings(_50K);
        final Input input = toInput(
                schema("Column1"),
                data(uniqueStrings));

        final Stopwatch stopwatch = createStarted();
        final Optional<Workbook> result = buildsWorkbookFromInputs.recon(input, input);
        stopwatch.stop();
        assertThat(stopwatch.elapsed(SECONDS), is(lessThan(10L)));
        assertFalse(result.isPresent());
    }

    @Ignore
    @Test
    public void performanceWithBreaks() {
        final List<String> lhsData = randomStrings(_50K).collect(toList());
        final List<String> rhsData = concat(
                lhsData.stream().limit(_25K),
                randomStrings(_25K))
                .collect(toList());
        shuffle(rhsData);
        final Input lhs = toInput(
                schema("Column1"),
                data(lhsData.stream()));
        final Input rhs = toInput(
                schema("Column1"),
                data(rhsData.stream()));

        final Stopwatch stopwatch = createStarted();
        buildsWorkbookFromInputs.recon(lhs, rhs);
        stopwatch.stop();
        assertThat(stopwatch.elapsed(SECONDS), is(lessThan(30L)));
    }

    @Before
    public void setUp() {
        buildsWorkbookFromInputs = getInstance(BuildsWorkbookFromInputs.class);
    }

    private BddWorksheet getWorksheet(final String name, final Input lhs, final Input rhs) {
        final BddWorkbook workbook = (BddWorkbook) buildsWorkbookFromInputs.recon(
                lhs, rhs).get();
        return workbook.getSheet(name);
    }

    private static String randomString() {
        return randomUUID().toString();
    }

    private static Stream<String> randomStrings(final long size) {
        return generate(SingleColumnKeyNoDataTest::randomString)
                .limit(size);
    }

}
