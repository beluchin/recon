package recon.adapters.app;

import recon.adapter.files.ComparesFiles;
import recon.internal.Module;

import static com.google.inject.Guice.createInjector;

public final class App {

    public static void main(final String[] args) {
        final String lhsFilename = args[0];
        final String rhsFilename = args[1];

        final ComparesFiles fileComparer = getComparer();
        fileComparer.apply(lhsFilename, rhsFilename);
    }

    private static ComparesFiles getComparer() {
        return createInjector(
                new recon.adapter.files.Module(),
                new Module())
                .getInstance(ComparesFiles.class);
    }

}
