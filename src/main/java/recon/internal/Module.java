package recon.internal;

import com.google.inject.AbstractModule;
import recon.BuildsWorkbookFromInputs;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(BuildsWorkbookFromInputs.class).to(BuildsWorkbookFromInputsImpl.class);
    }
}
