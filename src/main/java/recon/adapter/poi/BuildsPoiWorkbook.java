package recon.adapter.poi;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import recon.Workbook;
import recon.adapter.datamodel.PoiWorkbook;
import recon.adapter.datamodel.PoiWorksheet;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorkbook;

import static java.util.Arrays.asList;

class BuildsPoiWorkbook implements BuildsWorkbook {
    @Override
    public Workbook build(Worksheet... worksheets) {
        final HSSFWorkbook w = new HSSFWorkbook();
        add(worksheets, w);
        return new PoiWorkbook(w);
    }

    private static void add(final Worksheet[] sheets, final HSSFWorkbook book) {
        asList(sheets).forEach(s -> { add(s, book); });
    }

    @SuppressFBWarnings({"BC_UNCONFIRMED_CAST"})
    private static void add(final Worksheet s, HSSFWorkbook w) {
        PoiWorksheet poiSheet = (PoiWorksheet) s;
        poiSheet.addTo(w);
    }
}
