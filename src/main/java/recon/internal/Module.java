package recon.internal;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import recon.ComparesInputs;
import recon.ExcelWorkbook;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(ComparesInputs.class).to(ComparesInputsUsingSets.class);
    }

    @Provides
    ExcelWorkbook excelWorkbook() {
        return new ExcelWorkbook() {};
    }
}
