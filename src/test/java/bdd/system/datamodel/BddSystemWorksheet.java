package bdd.system.datamodel;

import bdd.datamodel.BddWorksheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;

public class BddSystemWorksheet implements BddWorksheet {
    private final XSSFSheet poiSheet;

    public BddSystemWorksheet(final XSSFSheet poiSheet) {
        this.poiSheet = poiSheet;
    }

    @Override
    public List<String> getRow(final int i) {
        final List<String> result = new ArrayList<>();
        poiSheet.getRow(i).cellIterator().forEachRemaining(
                c -> result.add(c.getStringCellValue()));
        return result;
    }
}
