package bdd.buslogic;

import bdd.buslogic.datamodel.BddExcelWorkbookPojo;
import recon.ExcelWorkbook;
import recon.internal.deps.BuildsExcelWorkbook;

public class BuildsBddExcelWorkbookPojo implements BuildsExcelWorkbook {
    @Override
    public ExcelWorkbook build() {
        return new BddExcelWorkbookPojo();
    }
}
