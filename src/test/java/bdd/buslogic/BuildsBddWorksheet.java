package bdd.buslogic;

import bdd.buslogic.datamodel.BddBusLogicWorksheet;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * not thread-safe
 */
public class BuildsBddWorksheet implements BuildsWorksheet {
    private final Map<Integer, List<String>> rows = new HashMap<>();

    @Override
    public Worksheet build(final String name) {
        return new BddBusLogicWorksheet(name, copyOf(rows));
    }

    @Override
    public BuildsWorksheet row(final int i, final List<String> values) {
        rows.put(i, values);
        return this;
    }

    private static Map<Integer, List<String>> copyOf(final Map<Integer, List<String>> map) {
        return new HashMap<>(map);
    }
}
