package bdd.system;

import bdd.system.utils.FileUtils;
import recon.BuildsWorkbookFromInputs;
import recon.Workbook;
import recon.Input;
import recon.adapter.app.App;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("AccessStaticViaInstance")
class BuildsWorkbookFromInputsUsingFiles implements BuildsWorkbookFromInputs {
    private final App app;
    private final ConvertsToWorkbook convertsToWorkbook;
    private final String defaultOutputFilename;
    private final FileUtils fileUtils;

    @Inject
    BuildsWorkbookFromInputsUsingFiles(
            final App app,
            final ConvertsToWorkbook convertsToWorkbook,
            @Named("recon.app.defaultOutputFilename")
            final String defaultOutputFilename,
            final FileUtils fileUtils) {
        this.app = app;
        this.convertsToWorkbook = convertsToWorkbook;
        this.defaultOutputFilename = defaultOutputFilename;
        this.fileUtils = fileUtils;
    }


    @Override
    public @Nullable
    Workbook recon(final Input lhs, final Input rhs) {
        final String lhsFilename = toTempFile(lhs);
        final String rhsFilename = toTempFile(rhs);
        app.main(new String[]{lhsFilename, rhsFilename});
        return getWorkbookFromOutputFile();
    }

    private Workbook getWorkbookFromOutputFile() {
        return convertsToWorkbook.convert(defaultOutputFilename);
    }

    private String toTempFile(final Input input) {
        return fileUtils.toTempFile(input);
    }

}
