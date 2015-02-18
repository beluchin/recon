package recon;

import java.util.List;
import java.util.stream.Stream;

public interface Input {
    interface Schema {
        List<String> get();
    }

    interface DataRow {
        List<String> get();
    }

    String getName();
    Schema getSchema();
    Stream<DataRow> getData();
}
