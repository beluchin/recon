package bdd.buslogic.datamodel;

import recon.internal.datamodel.Worksheet;

public class BddBusLogicWorksheet implements Worksheet {
    private final String name;

    public BddBusLogicWorksheet(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
