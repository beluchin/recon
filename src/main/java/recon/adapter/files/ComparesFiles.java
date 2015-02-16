package recon.adapter.files;

import com.google.common.collect.ImmutableList;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import recon.BuildsWorkbookFromInputs;
import recon.Input;
import recon.Input.DataRow;
import recon.Input.Schema;
import recon.adapter.datamodel.PoiWorkbook;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;

public class ComparesFiles {
    private final BuildsWorkbookFromInputs buildsWorkbookFromInputs;
    private final String defaultOutputFilename;

    @Inject
    public ComparesFiles(
            final BuildsWorkbookFromInputs buildsWorkbookFromInputs,
            @Named("recon.app.defaultOutputFilename")
                    final String defaultOutputFilename) {
        this.buildsWorkbookFromInputs = buildsWorkbookFromInputs;
        this.defaultOutputFilename = defaultOutputFilename;
    }

    public void apply(final String lhsFilename, final String rhsFilename) {
        final Input lhsInput = toInput(lhsFilename);
        final Input rhsInput = toInput(rhsFilename);
        removeEmptyDefaultOutputFile();
        final PoiWorkbook workbook = (PoiWorkbook) buildsWorkbookFromInputs.recon(
                lhsInput, rhsInput);
        if (workbook != null) {
            workbook.save(defaultOutputFilename);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressFBWarnings({"RV_RETURN_VALUE_IGNORED_BAD_PRACTICE"})
    private void removeEmptyDefaultOutputFile() {
        new File(defaultOutputFilename).delete();
    }

    @SuppressFBWarnings({"DM_DEFAULT_ENCODING"})
    private static FileReader newFileReader(final File file) {
        try {
            return new FileReader(file);
        } catch (final FileNotFoundException e) {
            throw propagate(e);
        }
    }

    private static String readLine(final BufferedReader r) {
        try {
            return r.readLine();
        } catch (final IOException e) {
            throw propagate(e);
        }
    }

    private static Stream<DataRow> toData(final BufferedReader reader) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<DataRow>(Long.MAX_VALUE, 0) {
            @Override
            public boolean tryAdvance(final Consumer<? super DataRow> action) {
                final String line = readLine(reader);
                if (line == null) {
                    return false;
                }
                action.accept(toDataRow(line));
                return true;
            }
        }, false);
    }

    private static DataRow toDataRow(final String line) {
        return () -> newArrayList(line.split(","));
    }

    private static Input toInput(final String lhsFilename) {
        final File file = new File(lhsFilename);
        final FileReader fileReader = newFileReader(file);
        final BufferedReader bufferedReader = new BufferedReader(fileReader);
        final String firstLine = readLine(bufferedReader);
        return new Input() {
            private final Schema schema = ComparesFiles.toSchema(firstLine);
            private final Stream<DataRow> data = ComparesFiles.toData(bufferedReader);

            @Override
            public Schema getSchema() {
                return schema;
            }

            @Override
            public Stream<DataRow> getData() {
                return data;
            }
        };
    }

    private static Schema toSchema(final String csv) {
        return () -> ImmutableList.copyOf(csv.split(","));
    }
}
