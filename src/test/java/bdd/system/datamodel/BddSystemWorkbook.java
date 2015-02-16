package bdd.system.datamodel;

import bdd.datamodel.BddWorkbook;
import bdd.datamodel.BddWorksheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Nullable;

public class BddSystemWorkbook implements BddWorkbook {
    private final XSSFWorkbook poiWorkbook;

    public BddSystemWorkbook(final XSSFWorkbook poiWorkbook) {
        this.poiWorkbook = poiWorkbook;
    }

    @Override
    public @Nullable BddWorksheet getSheet(final String name) {
        final XSSFSheet s = poiWorkbook.getSheet(name);
        if (s == null) {
            return null;
        }
        return new BddSystemWorksheet(s);
    }
}
