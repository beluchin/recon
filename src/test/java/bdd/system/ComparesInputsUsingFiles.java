package bdd.system;

import bdd.system.utils.FileUtils;
import recon.ComparesInputs;
import recon.ExcelWorkbook;
import recon.Input;
import recon.adapter.app.App;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("AccessStaticViaInstance")
class ComparesInputsUsingFiles implements ComparesInputs {
    private final App app;
    private final ConvertsToExcelWorkbook convertsToExcelWorkbook;
    private final String defaultOutputFilename;
    private final FileUtils fileUtils;

    @Inject
    ComparesInputsUsingFiles(
            final App app,
            final ConvertsToExcelWorkbook convertsToExcelWorkbook,
            @Named("recon.app.defaultOutputFilename")
                    final String defaultOutputFilename,
            final FileUtils fileUtils) {
        this.app = app;
        this.convertsToExcelWorkbook = convertsToExcelWorkbook;
        this.defaultOutputFilename = defaultOutputFilename;
        this.fileUtils = fileUtils;
    }


    @Override
    public @Nullable ExcelWorkbook recon(final Input lhs, final Input rhs) {
        final String lhsFilename = toTempFile(lhs);
        final String rhsFilename = toTempFile(rhs);
        app.main(new String[]{lhsFilename, rhsFilename});
        return getWorkbookFromOutputFile();
    }

    private ExcelWorkbook getWorkbookFromOutputFile() {
        return convertsToExcelWorkbook.convert(defaultOutputFilename);
    }

    private String toTempFile(final Input input) {
        return fileUtils.toTempFile(input);
    }

}
