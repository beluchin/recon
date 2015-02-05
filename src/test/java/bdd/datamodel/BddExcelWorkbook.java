package bdd.datamodel;

import recon.ExcelWorkbook;

import javax.annotation.Nullable;

public interface BddExcelWorkbook extends ExcelWorkbook {
    @Nullable String getSheet(String data);
}
