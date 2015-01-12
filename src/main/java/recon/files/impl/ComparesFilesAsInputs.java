package recon.files.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import recon.ComparesInputs;
import recon.datamodel.Input;
import recon.datamodel.Input.DataRow;
import recon.datamodel.Input.Schema;
import recon.files.ComparesFiles;

import javax.inject.Inject;
import java.io.*;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.google.common.base.Throwables.propagate;

class ComparesFilesAsInputs implements ComparesFiles {
    private final ComparesInputs comparesInputs;

    @Inject
    ComparesFilesAsInputs(final ComparesInputs comparesInputs) {
        this.comparesInputs = comparesInputs;
    }

    @Override
    public void apply(final String lhsFilename, final String rhsFilename) {
        final Input lhsInput = toInput(lhsFilename);
        final Input rhsInput = toInput(rhsFilename);

        comparesInputs.recon(lhsInput, rhsInput);
    }

    private static Stream<DataRow> toData(final BufferedReader reader) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<DataRow>(Long.MAX_VALUE, 0) {
            @Override
            public boolean tryAdvance(Consumer<? super DataRow> action) {
                String line = readLine(reader);
                if (line == null) {
                    return false;
                }
                action.accept(toDataRow(line));
                return true;
            }
        }, false);
    }

    private static DataRow toDataRow(final String line) {
        // TODO
        throw new UnsupportedOperationException();
    }

    private static Schema toSchema(final String csv) {
        return new Schema() {};
    }

    private static Input toInput(final String lhsFilename) {
        final File file = new File(lhsFilename);
        final FileReader fileReader = newFileReader(file);
        final BufferedReader bufferedReader = new BufferedReader(fileReader);
        final String firstLine = readLine(bufferedReader);
        return new Input() {
            private final Schema schema = ComparesFilesAsInputs.toSchema(firstLine);
            private final Stream<DataRow> data = ComparesFilesAsInputs.toData(bufferedReader);

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

    private static String readLine(final BufferedReader r) {
        try {
            return r.readLine();
        } catch (final IOException e) {
            throw propagate(e);
        }
    }

    @SuppressFBWarnings({"DM_DEFAULT_ENCODING"})
    private static FileReader newFileReader(final File file) {
        try {
            return new FileReader(file);
        } catch (final FileNotFoundException e) {
            throw propagate(e);
        }
    }
}
