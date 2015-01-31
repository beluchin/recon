package bdd.system;

import recon.Output;

import javax.annotation.Nullable;
import java.io.File;

class ConvertsToOutput {
    @Nullable public Output convert(final String fileName) {
        File f = new File(fileName);
        return f.exists() && !f.isDirectory()
                ? new Output() {}
                : null;
    }
}
