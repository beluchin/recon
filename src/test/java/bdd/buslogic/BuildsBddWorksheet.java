package bdd.buslogic;

import bdd.buslogic.datamodel.BddBusLogicWorksheet;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

import java.util.List;
import java.util.Map;

class BuildsBddWorksheet implements BuildsWorksheet {
    @Override
    public Worksheet build(
            final String name, final Map<Integer, List<String>> rows) {
        return new BddBusLogicWorksheet(name, rows);
    }
}
