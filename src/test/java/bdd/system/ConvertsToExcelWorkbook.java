package bdd.system;

import recon.ExcelWorkbook;

import javax.annotation.Nullable;
import java.io.File;

class ConvertsToExcelWorkbook {
    @Nullable public ExcelWorkbook convert(final String fileName) {
        File f = new File(fileName);
        return f.exists() && !f.isDirectory()
                ? new ExcelWorkbook() {}
                : null;
    }
}
