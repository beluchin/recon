package bdd;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import static com.google.common.base.Throwables.propagate;

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
        switch (getBddTarget()) {
            case BusLogic:
                return getBusLogicModules();
            case System:
                return getSystemModules();
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static BddTarget getBddTarget() {
        final PropertiesConfiguration p =
                getPropertiesConfiguration("bdd.properties");
        return BddTarget.valueOf((String) p.getProperty("bddTarget"));
    }

    private static Iterable<Module> getBusLogicModules() {
        return ImmutableList.<Module>of(
                new recon.internal.Module());
    }

    private static PropertiesConfiguration getPropertiesConfiguration(final String fileName) {
        try {
            return new PropertiesConfiguration(fileName);
        } catch (ConfigurationException e) {
            throw propagate(e);
        }
    }

    private static Iterable<Module> getSystemModules() {
        return ImmutableList.<Module> of(
                new bdd.system.Module(),
                new recon.adapter.files.Module()
                );
    }
}
