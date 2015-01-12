package bdd;

import org.junit.Before;
import org.junit.Test;
import recon.ComparesInputs;
import recon.datamodel.ExcelWorkbook;
import recon.datamodel.Input;

import static bdd.datamodel.impl.InputUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NoConfigTest extends AbstractBddTest {

    private ComparesInputs comparesInputs;

    @Test
    public void _1_noOutputIfInputsAreIdentical() {
        Input input = toInput(
                schema("Column1"),
                dataRow("Hello"));
        ExcelWorkbook result = comparesInputs.recon(input, input);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void _2_nonNullOutputFilesAreNotIdentical() {
        Input lhs = toInput(
                schema("Column1"),
                dataRow("Hello"));
        Input rhs = toInput(
                schema("Column1"),
                dataRow("World"));
        ExcelWorkbook result = comparesInputs.recon(lhs, rhs);
        assertThat(result, is(not(nullValue())));
    }

    @Before
    public void setUp() {
        comparesInputs = getInstance(ComparesInputs.class);
    }

}
