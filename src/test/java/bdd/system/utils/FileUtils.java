package bdd.system.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import recon.Input;
import recon.Input.DataRow;
import recon.Input.Schema;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

import static com.google.common.base.Throwables.propagate;

public final class FileUtils {

    private static final File TempDir = SystemUtils.IS_OS_WINDOWS
            ? notImplemented()
            : new File("/tmp");

    public FileUtils() {
        // no-op
    }

    public static File createFileInTempDir(final String filename) {
        return new File(TempDir + "/" + filename);
    }

    public static String toFile(final Input in) {
        final File file = createFileInTempDir(in.getName() + ".tmp");
        addTo(file, in.getSchema(), in.getData());
        return file.getAbsolutePath();
    }

    private static void addTo(
            final File file, final Schema schema, final Stream<DataRow> data) {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            write(bw, separateLine(toCsv(schema.get())));
            data.forEach(r -> write(bw, separateLine(toCsv(r.get()))));
            bw.close();
        } catch (final IOException e) {
            throw propagate(e);
        }
    }

    private static File notImplemented() {
        throw new NotImplementedException();
    }

    private static String separateLine(final String s) {
        return s + '\n';
    }

    private static String toCsv(final Iterable<String> xs) {
        return StringUtils.join(xs, ',');
    }

    private static void write(final BufferedWriter w, final String s) {
        try {
            w.write(s);
        } catch (final IOException e) {
            throw propagate(e);
        }
    }

}
