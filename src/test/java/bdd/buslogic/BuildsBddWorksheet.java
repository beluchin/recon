package bdd.buslogic;

import bdd.buslogic.datamodel.BddBusLogicWorksheet;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorksheet;

public class BuildsBddWorksheet implements BuildsWorksheet {
    @Override
    public Worksheet build(final String name) {
        return new BddBusLogicWorksheet(name);
    }
}
