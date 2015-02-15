package recon;

import javax.annotation.Nullable;

public interface BuildsWorkbookFromInputs {
    @Nullable
    Workbook recon(Input lhs, Input rhs);
}
