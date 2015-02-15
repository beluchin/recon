package recon.adapter.datamodel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import recon.internal.datamodel.Worksheet;

public class PoiWorksheet implements Worksheet {
    private final String name;

    public PoiWorksheet(final String name) {
        this.name = name;
    }

    public void addTo(final HSSFWorkbook w) {
        w.createSheet(name);
    }
}
