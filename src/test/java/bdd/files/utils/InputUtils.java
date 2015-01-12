package bdd.files.utils;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.SystemUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.google.common.base.Throwables.propagate;
import static java.util.Arrays.stream;

public final class InputUtils {

    public final static class DataRow {

        private final String[] strings;

        public DataRow(final String[] strings) {
            this.strings = strings;
        }

        public String toCommaSeparatedString() {
            return Joiner.on(',').join(strings);
        }

    }

    public final static class Schema {

        private final String[] strings;

        public Schema(final String[] strings) {
            this.strings = strings;
        }

        public String toCommaSeparatedString() {
            return Joiner.on(',').join(strings);
        }

    }

    private static final File TempDir = SystemUtils.IS_OS_WINDOWS
            ? notImplemented()
            : new File("/tmp");

    private InputUtils() {
        // disabled default constructor
    }

    public static File createTempFile(final String prefix) {
        try {
            return File.createTempFile(prefix, ".tmp", TempDir);
        } catch (final IOException e) {
            throw propagate(e);
        }
    }

    public static DataRow dataRow(final String... strings) {
        return new DataRow(strings);
    }

    public static void deleteTempFilesWithPrefix(final String prefix) {
        final File[] files = TempDir.listFiles((dir, name) -> name.matches(prefix + ".*"));
        stream(files).forEach(File::delete);
    }

    public static Schema schema(final String... strings) {
        return new Schema(strings);
    }

    public static String toTempFile(
            final String prefix, final Schema schema, final DataRow... dataRows) {
        final File file = createTempFile(prefix);
        addTo(file, schema, dataRows);
        return file.getAbsolutePath();
    }

    private static File notImplemented() {
        throw new NotImplementedException();
    }

    private static void addTo(final File file, final Schema schema, final DataRow[] dataRows) {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(separateLine(schema.toCommaSeparatedString()));
            for (final DataRow r : dataRows) {
                bw.write(separateLine(r.toCommaSeparatedString()));
            }
            bw.close();
        } catch (final IOException e) {
            throw propagate(e);
        }
    }

    private static String separateLine(final String s) {
        return s + '\n';
    }

}
