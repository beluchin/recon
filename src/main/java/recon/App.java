package recon;

import recon.api.ComparesFiles;

import static com.google.inject.Guice.createInjector;

public final class App {

    public static void main(final String[] args) {
        final String lhsFilename = args[0];
        final String rhsFilename = args[0];

        final ComparesFiles fileComparer = getComparer();
        fileComparer.recon(lhsFilename, rhsFilename);
    }

    private static ComparesFiles getComparer() {
        return createInjector(
                new recon.api.Module(),
                new recon.businesslogic.Module())
                .getInstance(ComparesFiles.class);
    }

}
