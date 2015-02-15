package bdd.buslogic.datamodel;

import bdd.datamodel.BddWorkbook;
import recon.internal.datamodel.Worksheet;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class BddBusLogicWorkbook implements BddWorkbook {
    private final Stream<BddBusLogicWorksheet> worksheets;

    public BddBusLogicWorkbook(final Stream<BddBusLogicWorksheet> worksheets) {
        this.worksheets = worksheets;
    }

    @Override
    public @Nullable String getSheet(final String name) {
        return worksheets.filter(s -> s.getName().equals(name)).findFirst().isPresent()
                ? name: null;
    }
}
