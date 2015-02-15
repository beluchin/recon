package bdd.tests;

import bdd.AbstractBddTest;
import bdd.datamodel.BddWorkbook;
import com.google.common.base.Stopwatch;
import org.junit.Before;
import org.junit.Test;
import recon.BuildsWorkbookFromInputs;
import recon.Workbook;
import recon.Input;

import java.util.stream.Stream;

import static bdd.datamodel.InputUtils.data;
import static bdd.datamodel.InputUtils.dataRow;
import static bdd.datamodel.InputUtils.schema;
import static bdd.datamodel.InputUtils.toInput;
import static com.google.common.base.Stopwatch.createStarted;
import static java.util.UUID.randomUUID;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Stream.generate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class SingleColumnKeyNoDataTest extends AbstractBddTest {

    private BuildsWorkbookFromInputs buildsWorkbookFromInputs;

    @Test
    public void _1_noOutputIfInputsAreIdentical() {
        final Input input = toInput(
                schema("Column1"),
                dataRow("Hello"));
        final Workbook result = buildsWorkbookFromInputs.recon(input, input);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void _2_outputIfInputsAreNotIdentical() {
        final Input lhs = toInput(
                schema("Column1"),
                dataRow("Hello"));
        final Input rhs = toInput(
                schema("Column1"),
                dataRow("World"));
        final Workbook result = buildsWorkbookFromInputs.recon(lhs, rhs);
        assertThat(result, is(not(nullValue())));
    }

    @Test
    public void _3_workbookHasDataWorksheet() {
        final Input lhs = toInput(
                schema("Column1"),
                dataRow("Hello"));
        final Input rhs = toInput(
                schema("Column1"),
                dataRow("World"));
        final BddWorkbook result = (BddWorkbook) buildsWorkbookFromInputs.recon(lhs, rhs);
        //noinspection ConstantConditions
        assertThat(result.getSheet("data"), is(not(nullValue())));
    }

    @Test
    public void performanceOnlyKeys() {
        final long _50K = 50000;
        final Stream<String> uniqueStrings = generate(SingleColumnKeyNoDataTest::randomString)
                .limit(_50K);
        final Input input = toInput(
                schema("Column1"),
                data(uniqueStrings));

        final Stopwatch stopwatch = createStarted();
        final Workbook result = buildsWorkbookFromInputs.recon(input, input);
        stopwatch.stop();
        assertThat(stopwatch.elapsed(SECONDS), is(lessThan(10L)));
        assertThat(result, is(nullValue()));
    }

    @Before
    public void setUp() {
        buildsWorkbookFromInputs = getInstance(BuildsWorkbookFromInputs.class);
    }

    private static String randomString() {
        return randomUUID().toString();
    }

}
