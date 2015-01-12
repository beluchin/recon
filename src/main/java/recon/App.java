package recon;

import com.google.inject.Guice;
import recon.files.ComparesFiles;
import recon.files.impl.Module;

public final class App {

    public static void main(final String[] args) {
        final String lhsFilename = args[0];
        final String rhsFilename = args[0];

        final ComparesFiles fileComparer = getComparer();
        fileComparer.apply(lhsFilename, rhsFilename);
    }

    private static ComparesFiles getComparer() {
        return Guice.createInjector(
                new Module(),
                new recon.impl.Module(),
                new recon.datamodel.impl.Module())
                .getInstance(ComparesFiles.class);
    }

}
