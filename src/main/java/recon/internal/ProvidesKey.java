package recon.internal;

import recon.Input;
import recon.config.Key;

import java.util.List;

class ProvidesKey {
    public Key get(final Input lhs, final Input rhs) {
        return () -> lhs.getSchema().get();
    }
}
