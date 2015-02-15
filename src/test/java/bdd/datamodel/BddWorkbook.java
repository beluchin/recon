package bdd.datamodel;

import recon.Workbook;

import javax.annotation.Nullable;

public interface BddWorkbook extends Workbook {
    @Nullable String getSheet(String data);
}
