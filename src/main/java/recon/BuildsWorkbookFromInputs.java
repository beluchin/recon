package recon;

import java.util.Optional;

public interface BuildsWorkbookFromInputs {
    Optional<Workbook> recon(Input lhs, Input rhs);
}
