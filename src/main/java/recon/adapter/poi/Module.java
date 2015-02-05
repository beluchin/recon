/*
 * Created by IntelliJ IDEA.
 * User: beluchin
 * Date: 2/5/15
 * Time: 6:37 AM
 */
package recon.adapter.poi;

import com.google.inject.AbstractModule;
import recon.internal.deps.BuildsExcelWorkbook;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(BuildsExcelWorkbook.class).to(BuildsPoiWorkbook.class);
    }
}
