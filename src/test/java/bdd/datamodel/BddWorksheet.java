package bdd.datamodel;

import recon.internal.datamodel.Worksheet;

import java.util.List;

public interface BddWorksheet extends Worksheet {
    List<String> getRow(int i);
}
