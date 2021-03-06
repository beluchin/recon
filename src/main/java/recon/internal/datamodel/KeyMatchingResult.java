package recon.internal.datamodel;

import org.apache.commons.lang3.tuple.Pair;
import recon.Input.DataRow;

import java.util.List;

public class KeyMatchingResult {

    public final List<Pair<DataRow, DataRow>> reconRows;
    public final Pair<List<DataRow>, List<DataRow>> populationBreaks;
    public final Pair<List<DataRow>, List<DataRow>> duplicates;

    public KeyMatchingResult(
            final List<Pair<DataRow, DataRow>> reconRows,
            final Pair<List<DataRow>, List<DataRow>> populationBreaks,
            final Pair<List<DataRow>, List<DataRow>> duplicates) {
        this.reconRows = reconRows;
        this.populationBreaks = populationBreaks;
        this.duplicates = duplicates;
    }

}
