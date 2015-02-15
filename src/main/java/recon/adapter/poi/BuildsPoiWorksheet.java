package recon.adapter.poi;

import recon.adapter.datamodel.PoiWorksheet;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

public class BuildsPoiWorksheet implements BuildsWorksheet {
    @Override
    public Worksheet build(String name) {
        return new PoiWorksheet(name);
    }
}
