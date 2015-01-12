package recon.files.impl;

import com.google.inject.AbstractModule;
import recon.files.ComparesFiles;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(ComparesFiles.class).to(ComparesFilesAsInputs.class);
    }
}
