package recon.datamodel.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import recon.datamodel.ExcelWorkbook;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    ExcelWorkbook excelWorkbook() {
        return new ExcelWorkbook() {};
    }
}
