package bdd.buslogic.datamodel;

import bdd.datamodel.BddWorksheet;

import java.util.List;

public class BddBusLogicWorksheet implements BddWorksheet {
    private final String name;

    public BddBusLogicWorksheet(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public List<String> getRow(final int i) {
        // TODO: not implemented
        throw new UnsupportedOperationException();
    }
}
