package bdd.system;

import bdd.system.utils.FileUtils;
import recon.BuildsWorkbookFromInputs;
import recon.Workbook;
import recon.Input;
import recon.adapter.app.App;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

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
    public Optional<Workbook> recon(final Input lhs, final Input rhs) {
        final String lhsFilename = toFile(lhs);
        final String rhsFilename = toFile(rhs);
        app.main(new String[]{lhsFilename, rhsFilename});
        return getWorkbookFromOutputFile();
    }

    private Optional<Workbook> getWorkbookFromOutputFile() {
        return convertsToWorkbook.convert(defaultOutputFilename);
    }

    private String toFile(final Input input) {
        return fileUtils.toFile(input);
    }

}
