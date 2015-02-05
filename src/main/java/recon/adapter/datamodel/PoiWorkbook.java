package recon.adapter.datamodel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import recon.ExcelWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.google.common.base.Throwables.propagate;

public class PoiWorkbook implements ExcelWorkbook {
    private final HSSFWorkbook workbook;

    public PoiWorkbook(final HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public void save(final String filename) {
        try {
            workbook.write(getStream(filename));
        } catch (IOException e) {
            throw propagate(e);
        }
    }

    private FileOutputStream getStream(final String filename) {
        try {
            return new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            throw propagate(e);
        }
    }

    @Override
    public void addSheet(final String name) {
        workbook.createSheet(name);
    }
}
