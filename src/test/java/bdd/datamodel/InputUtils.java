package bdd.datamodel;

import com.google.common.collect.ImmutableList;
import recon.Input;
import recon.Input.DataRow;
import recon.Input.Schema;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.UUID.randomUUID;

public class InputUtils {

    public static DataRow[] data(final Stream<String> csvDataRows) {
        return csvDataRows.map(InputUtils::dataRow).toArray(s -> new DataRow[s]);
    }

    public static DataRow dataRow(final String csv) {
        return new DataRow() {
            private final List<String> tokens = ImmutableList.copyOf(csv.split(","));
            @Override
            public List<String> get() {
                return tokens;
            }
        };
    }

    public static Schema schema(final String csv) {
        return () -> ImmutableList.of(csv);
    }

    public static Input toInput(final Schema schema, final DataRow... dataRows) {
        return toInput(randomUUID().toString(), schema, dataRows);
    }

    public static Input toInput(
            final String name, final Schema schema, final DataRow... dataRows) {
        return new Input() {
            @Override
            public String getName() { return name; }
            @Override
            public Schema getSchema() {
                return schema;
            }
            @Override
            public Stream<DataRow> getData() {
                return stream(dataRows);
            }
        };
    }
}
