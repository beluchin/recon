package bdd;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class AbstractBddTest {
    private Injector injector;

    protected AbstractBddTest() {
        injector = Guice.createInjector(getModules());
    }

    public <T> T getInstance(Class<T> c) {
        return getInjector().getInstance(c);
    }

    public Injector getInjector() {
        return injector;
    }

    private Iterable<Module> getModules() {
        return ImmutableList.<Module> of(
                new recon.impl.Module(),
                new recon.datamodel.impl.Module());
    }
}
