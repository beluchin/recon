package recon.adapter.datamodel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import recon.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.google.common.base.Throwables.propagate;

public class PoiWorkbook implements Workbook {
    private final XSSFWorkbook workbook;

    public PoiWorkbook(final XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public void save(final String filename) {
        try {
            final FileOutputStream stream = getStream(filename);
            workbook.write(stream);
            stream.close();
        } catch (final IOException e) {
            throw propagate(e);
        }
    }

    private FileOutputStream getStream(final String filename) {
        try {
            return new FileOutputStream(filename);
        } catch (final FileNotFoundException e) {
            throw propagate(e);
        }
    }
}
