package bdd.system;

import bdd.system.datamodel.BddSystemWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import recon.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import static com.google.common.base.Throwables.propagate;

class ConvertsToWorkbook {
    public Optional<Workbook> convert(final String fileName) {
        if (!fileExists(fileName)) {
            return Optional.empty();
        }

        final XSSFWorkbook w = getPoiWorkbook(fileName);
        return Optional.of(new BddSystemWorkbook(w));
    }

    private static boolean fileExists(final String n) {
        final File f = new File(n);
        return f.exists() && !f.isDirectory();
    }

    private static XSSFWorkbook getPoiWorkbook(final String fileName) {
        try {
            return new XSSFWorkbook(getInputStream(fileName));
        } catch (final IOException e) {
            throw propagate(e);
        }
    }

    private static FileInputStream getInputStream(final String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (final FileNotFoundException e) {
            throw propagate(e);
        }
    }
}
