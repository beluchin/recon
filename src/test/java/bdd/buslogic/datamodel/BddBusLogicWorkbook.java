package bdd.buslogic.datamodel;

import bdd.datamodel.BddWorkbook;
import bdd.datamodel.BddWorksheet;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

public class BddBusLogicWorkbook implements BddWorkbook {
    private final Stream<BddBusLogicWorksheet> worksheets;

    public BddBusLogicWorkbook(final Stream<BddBusLogicWorksheet> worksheets) {
        this.worksheets = worksheets;
    }

    @Override
    public @Nullable BddWorksheet getSheet(final String name) {
        final Optional<BddBusLogicWorksheet> sheet = worksheets
                .filter(s -> s.getName().equals(name))
                .findFirst();
        if (!sheet.isPresent()) {
            return null;
        }
        return sheet.get();
    }
}
