package recon.adapter.datamodel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import recon.internal.datamodel.Worksheet;

import java.util.List;
import java.util.Map;

import static java.util.stream.IntStream.range;

public class PoiWorksheet implements Worksheet {
    private final String name;
    private final Map<Integer, List<String>> rows;

    public PoiWorksheet(final String name, final Map<Integer, List<String>> rows) {
        this.name = name;
        this.rows = rows;
    }

    public void addTo(final XSSFWorkbook w) {
        final XSSFSheet sheet = w.createSheet(name);
        rows.forEach((k, v) -> addRow(sheet, k, v));
    }

    private static void addRow(
            final XSSFSheet sheet,
            final Integer rowNumber,
            final List<String> values) {
        final XSSFRow row = sheet.createRow(rowNumber);
        range(0, values.size())
                .forEach(i -> setCell(row, i, values.get(i)));
    }

    private static void setCell(final XSSFRow row, final int i, final String s) {
        row.createCell(i).setCellValue(s);
    }
}
