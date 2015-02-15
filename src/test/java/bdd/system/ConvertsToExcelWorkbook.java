package bdd.system;

import bdd.datamodel.BddWorkbook;
import bdd.datamodel.BddWorksheet;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import recon.Workbook;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.google.common.base.Throwables.propagate;

class ConvertsToExcelWorkbook {
    @Nullable
    public Workbook convert(final String fileName) {
        if (!fileExists(fileName)) {
            return null;
        }

        final HSSFWorkbook w = getHssfWorkbook(fileName);
        return new BddWorkbook() {
            @Override
            public @Nullable BddWorksheet getSheet(final String name) {
                // TODO
                throw new UnsupportedOperationException();
            }
        };
    }

    private static boolean fileExists(final String n) {
        File f = new File(n);
        return f.exists() && !f.isDirectory();
    }

    private static HSSFWorkbook getHssfWorkbook(final String fileName) {
        try {
            return new HSSFWorkbook(getInputStream(fileName));
        } catch (IOException e) {
            throw propagate(e);
        }
    }

    private static FileInputStream getInputStream(final String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw propagate(e);
        }
    }
}
