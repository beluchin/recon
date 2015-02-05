package bdd.system;

import bdd.datamodel.BddExcelWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import recon.ExcelWorkbook;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.google.common.base.Throwables.propagate;

class ConvertsToExcelWorkbook {
    @Nullable
    public ExcelWorkbook convert(final String fileName) {
        if (!fileExists(fileName)) {
            return null;
        }

        final HSSFWorkbook w = getHssfWorkbook(fileName);
        return new BddExcelWorkbook() {
            @Override
            public @Nullable String getSheet(final String name) {
                return w.getSheet(name) != null? name: null;
            }

            @Override
            public void addSheet(final String name) {
                w.createSheet(name);
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
