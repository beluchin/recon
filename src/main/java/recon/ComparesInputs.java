package recon;

import javax.annotation.Nullable;

public interface ComparesInputs {
    @Nullable
    ExcelWorkbook recon(Input lhs, Input rhs);
}
