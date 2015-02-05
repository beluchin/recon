package recon.internal;

import com.google.inject.AbstractModule;
import recon.ComparesInputs;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(ComparesInputs.class).to(ComparesInputsUsingSets.class);
    }
}
