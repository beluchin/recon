package recon.internal.deps;

import recon.internal.datamodel.Worksheet;

import java.util.List;
import java.util.Map;

public interface BuildsWorksheet {
    Worksheet build(String name, Map<Integer, List<String>> rows);
}
