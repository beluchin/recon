package recon.config;

import java.util.List;

public interface Key {
    /**
     * @return the list of column names on the key, in the order to appear
     * on the workbook
     */
    List<String> asList();
}
