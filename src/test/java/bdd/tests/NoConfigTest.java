package bdd.tests;

import bdd.AbstractBddTest;
import com.google.common.base.Stopwatch;
import org.junit.Before;
import org.junit.Test;
import recon.ComparesInputs;
import recon.Input;
import recon.Output;

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

public class NoConfigTest extends AbstractBddTest {

    private ComparesInputs comparesInputs;

    @Test
    public void _1_noOutputIfInputsAreIdentical() {
        Input input = toInput(
                schema("Column1"),
                dataRow("Hello"));
        Output result = comparesInputs.recon(input, input);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void _2_outputIfInputsAreNotIdentical() {
        Input lhs = toInput(
                schema("Column1"),
                dataRow("Hello"));
        Input rhs = toInput(
                schema("Column1"),
                dataRow("World"));
        Output result = comparesInputs.recon(lhs, rhs);
        assertThat(result, is(not(nullValue())));
    }

    @Test
    public void _3_performanceOnlyKeys() {
        final long _50K = 50000;
        final Stream<String> uniqueStrings = generate(NoConfigTest::randomString)
                .limit(_50K);
        Input input = toInput(
                schema("Column1"),
                data(uniqueStrings));

        Stopwatch stopwatch = createStarted();
        Output result = comparesInputs.recon(input, input);
        stopwatch.stop();
        assertThat(stopwatch.elapsed(SECONDS), is(lessThan(10L)));
        assertThat(result, is(nullValue()));
    }

    @Before
    public void setUp() {
        comparesInputs = getInstance(ComparesInputs.class);
    }

    private static String randomString() {
        return randomUUID().toString();
    }

}
