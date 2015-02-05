/*
 * Created by IntelliJ IDEA.
 * User: beluchin
 * Date: 2/4/15
 * Time: 5:46 AM
 */
package bdd.buslogic;

import com.google.inject.AbstractModule;
import recon.internal.deps.BuildsExcelWorkbook;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(BuildsExcelWorkbook.class).to(BuildsBddExcelWorkbookPojo.class);
    }
}
