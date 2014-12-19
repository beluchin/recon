package recon;

import mockit.Expectations;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import recon.utils.InputUtils;
import recon.utils.InputUtils.DataRow;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Throwables.propagate;
import static extensions.commons.lang3.SystemUtils.currentDirectory;
import static extensions.hamcrest.FilePresenceMatcher.existsIn;
import static org.hamcrest.MatcherAssert.assertThat;
import static recon.utils.InputUtils.dataRow;
import static recon.utils.InputUtils.schema;

@RunWith(JMockit.class)
public class NoConfigTest {

    @Test
    public void _1_writesMessageToConsoleIfFilesAreIdentical() {
        final String lhsFilename = toTempFile(
                schema("Column1"),
                dataRow("Hello"));
        final String rhsFilename = copyToTempFile(lhsFilename);
        captureTheMessagesToTheConsole();

        recon.App.main(new String[]{lhsFilename, rhsFilename});

        new Verifications() {{
            System.out.println("files are identical");
        }};
    }

    @Test
    public void _2_writesOutputFileToCurrentDirIfFilesAreNotIdentical() {
        final InputUtils.Schema schema = schema("Column1");
        final String lhsFilename = toTempFile(schema, dataRow("Hello"));
        final String rhsFilename = toTempFile(schema, dataRow("World"));

        recon.App.main(new String[] {lhsFilename, rhsFilename});

        assertThat("output.xlsx", existsIn(currentDirectory()));
    }

    @Before
    public void setUp() {
        deleteTempFiles();
    }

    @After
    public void tearDown() {
        /*
        do not delete the temp files so they can be inspected
        after the test finishes.
        */
    }

    private void captureTheMessagesToTheConsole() {
        new Expectations(System.out) {{
            System.out.println((String) any);
        }};
    }

    private void deleteTempFiles() {
        InputUtils.deleteTempFilesWithPrefix(NoConfigTest.class.getSimpleName());
    }

    private static String copyToTempFile(final String filename) {
        File result = InputUtils.createTempFile(NoConfigTest.class.getSimpleName());
        final boolean DOES_NOT_MATTER = true;
        try {
            FileUtils.copyFile(new File(filename), result, DOES_NOT_MATTER);
        } catch (IOException e) {
            throw propagate(e);
        }
        return result.getAbsolutePath();
    }

    private static String toTempFile(
            final InputUtils.Schema schema, final DataRow... rows)   {
        return InputUtils.toTempFile(
                NoConfigTest.class.getSimpleName(), schema, rows);
    }

}
