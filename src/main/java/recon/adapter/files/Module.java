package recon.adapter.files;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(Names.named("recon.app.defaultOutputFilename"))
                .toInstance("output.xlsx");
    }
}
