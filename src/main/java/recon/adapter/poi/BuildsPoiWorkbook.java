package recon.adapter.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import recon.ExcelWorkbook;
import recon.adapter.datamodel.PoiWorkbook;
import recon.internal.deps.BuildsExcelWorkbook;

class BuildsPoiWorkbook implements BuildsExcelWorkbook {
    @Override
    public ExcelWorkbook build() {
        final HSSFWorkbook w = new HSSFWorkbook();
        return new PoiWorkbook(w);
    }
}
