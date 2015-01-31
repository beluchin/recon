package recon.internal;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import recon.ComparesInputs;
import recon.Output;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(ComparesInputs.class).to(ComparesInputsUsingSets.class);
    }

    @Provides
    Output output() {
        return new Output() {};
    }
}
