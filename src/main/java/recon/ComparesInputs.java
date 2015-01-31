package recon;

import javax.annotation.Nullable;

public interface ComparesInputs {
    @Nullable
    Output recon(Input lhs, Input rhs);
}
