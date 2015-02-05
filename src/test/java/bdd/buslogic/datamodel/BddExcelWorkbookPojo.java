package bdd.buslogic.datamodel;

import bdd.datamodel.BddExcelWorkbook;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class BddExcelWorkbookPojo implements BddExcelWorkbook {
    Set<String> worksheets = new HashSet<>();

    @Override
    public void addSheet(final String name) {
        if (! worksheets.add(name)) {
            throw new IllegalArgumentException(name + " is already a worksheet");
        }
    }

    @Override
    public @Nullable String getSheet(final String name) {
        return worksheets.contains(name)? name: null;
    }
}
