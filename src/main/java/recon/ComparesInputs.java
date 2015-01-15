package recon;

import recon.datamodel.Output;
import recon.datamodel.Input;

import javax.annotation.Nullable;

public interface ComparesInputs {
    @Nullable
    Output recon(Input lhs, Input rhs);
}
