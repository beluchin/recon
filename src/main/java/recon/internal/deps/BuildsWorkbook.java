package recon.internal.deps;

import recon.Workbook;
import recon.internal.datamodel.Worksheet;

public interface BuildsWorkbook {
    Workbook build(Worksheet... worksheets);
}
