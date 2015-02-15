package bdd.datamodel;

import recon.Workbook;

import javax.annotation.Nullable;

public interface BddWorkbook extends Workbook {
    @Nullable BddWorksheet getSheet(String data);
}
