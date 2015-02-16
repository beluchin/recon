package bdd.buslogic.datamodel;

import bdd.datamodel.BddWorksheet;

import java.util.List;
import java.util.Map;

public class BddBusLogicWorksheet implements BddWorksheet {
    private final String name;
    private final Map<Integer, List<String>> rows;

    public BddBusLogicWorksheet(
            final String name, final Map<Integer, List<String>> rows) {
        this.name = name;
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    @Override
    public List<String> getRow(final int i) {
        return rows.get(i);
    }
}
