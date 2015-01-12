package recon;

import recon.datamodel.ExcelWorkbook;
import recon.datamodel.Input;

public interface ComparesInputs {
    ExcelWorkbook recon(Input lhs, Input rhs);
}
