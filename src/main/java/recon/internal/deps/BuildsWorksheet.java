package recon.internal.deps;

import recon.internal.datamodel.Worksheet;

import java.util.List;

public interface BuildsWorksheet {
    Worksheet build(String name);
    BuildsWorksheet row(int i, List<String> values);
}
