package recon.adapter.poi;

import recon.adapter.datamodel.PoiWorksheet;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

import java.util.List;

public class BuildsPoiWorksheet implements BuildsWorksheet {
    @Override
    public Worksheet build(String name) {
        return new PoiWorksheet(name);
    }

    @Override
    public BuildsWorksheet row(final int i, final List<String> values) {
        // TODO: not implemented
        throw new UnsupportedOperationException();
    }
}
