package bdd.datamodel.impl;

import recon.datamodel.Input;
import recon.datamodel.Input.DataRow;
import recon.datamodel.Input.Schema;

import java.util.Arrays;
import java.util.stream.Stream;

public class InputUtils {

    public static DataRow[] data(final Stream<String> csvDataRows) {
        return csvDataRows.map(InputUtils::dataRow).toArray(s -> new DataRow[s]);
    }

    public static DataRow dataRow(final String csv) {
        return new DataRow() {
            private final String[] tokens = csv.split(",");

            @Override
            public String get(final int index) {
                return tokens[index];
            }
        };
    }

    public static Schema schema(final String csv) {
        return new Schema() {
        };
    }

    public static Input toInput(final Schema schema, final DataRow... dataRows) {
        return new Input() {
            @Override
            public Schema getSchema() {
                return schema;
            }

            @Override
            public Stream<DataRow> getData() {
                return Arrays.stream(dataRows);
            }
        };
    }
}
