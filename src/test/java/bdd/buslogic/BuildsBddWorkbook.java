package bdd.buslogic;

import bdd.buslogic.datamodel.BddBusLogicWorkbook;
import bdd.buslogic.datamodel.BddBusLogicWorksheet;
import recon.Workbook;
import recon.internal.datamodel.Worksheet;
import recon.internal.deps.BuildsWorkbook;

import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

public class BuildsBddWorkbook implements BuildsWorkbook {
    @Override
    public Workbook build(final Worksheet... worksheets) {
        final Stream<BddBusLogicWorksheet> bddWorksheets = newArrayList(worksheets)
                .stream()
                .map((s) -> toBddBusLogicWorksheet(s));
        return new BddBusLogicWorkbook(bddWorksheets);
    }

    private static BddBusLogicWorksheet toBddBusLogicWorksheet(
            Worksheet s) {
        return (BddBusLogicWorksheet)s;
    }

}
