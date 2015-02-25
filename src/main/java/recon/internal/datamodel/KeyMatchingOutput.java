package recon.internal.datamodel;

import org.apache.commons.lang3.tuple.ImmutablePair;
import recon.Input.DataRow;

import java.util.ArrayList;
import java.util.List;

public class KeyMatchingOutput {
    public static class Builder {
        private List<ImmutablePair<DataRow, DataRow>> reconRows = new ArrayList<>();

        public Builder reconRows(final List<ImmutablePair<DataRow, DataRow>> reconRows) {
            this.reconRows = reconRows;
            return this;
        }

        public KeyMatchingOutput build() {
            return new KeyMatchingOutput(reconRows);
        }
    }

    public final List<ImmutablePair<DataRow, DataRow>> reconRows;

    private KeyMatchingOutput(final List<ImmutablePair<DataRow, DataRow>> reconRows) {
        this.reconRows = reconRows;
    }
}
