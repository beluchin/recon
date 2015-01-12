package recon.datamodel;

import java.util.stream.Stream;

public interface Input {
    interface Schema {
    }

    interface DataRow {
        String get(final int index);
    }

    Schema getSchema();
    Stream<DataRow> getData();
}
