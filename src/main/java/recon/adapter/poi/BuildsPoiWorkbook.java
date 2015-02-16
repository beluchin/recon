package recon.adapter.poi;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import recon.Workbook;
import recon.adapter.datamodel.PoiWorkbook;
import recon.adapter.datamodel.PoiWorksheet;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorkbook;

import static java.util.Arrays.asList;

class BuildsPoiWorkbook implements BuildsWorkbook {
    @Override
    public Workbook build(final Worksheet... worksheets) {
        final XSSFWorkbook w = new XSSFWorkbook();
        add(worksheets, w);
        return new PoiWorkbook(w);
    }

    private static void add(final Worksheet[] sheets, final XSSFWorkbook book) {
        asList(sheets).forEach(s -> add(s, book));
    }

    @SuppressFBWarnings({"BC_UNCONFIRMED_CAST"})
    private static void add(final Worksheet s, final XSSFWorkbook w) {
        final PoiWorksheet poiSheet = (PoiWorksheet) s;
        poiSheet.addTo(w);
    }
}
