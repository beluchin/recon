package recon.datamodel.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import recon.datamodel.Output;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    Output output() {
        return new Output() {};
    }
}
