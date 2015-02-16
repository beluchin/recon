package recon.adapter.poi;

import recon.adapter.datamodel.PoiWorksheet;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

import java.util.List;
import java.util.Map;

class BuildsPoiWorksheet implements BuildsWorksheet {
    @Override
    public Worksheet build(String name, final Map<Integer, List<String>> rows) {
        return new PoiWorksheet(name, rows);
    }
}
