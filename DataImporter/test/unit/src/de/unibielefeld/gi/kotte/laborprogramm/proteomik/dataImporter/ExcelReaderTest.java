package de.unibielefeld.gi.kotte.laborprogramm.proteomik.dataImporter;

import jxl.Workbook;
import java.io.File;
import org.junit.Test;

/**
 *
 * @author kotte
 */
public class ExcelReaderTest {

    @Test
    public void testStuff() {

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("resources/Export_1.xlsx"));
        } catch (Exception e) {
            System.out.println("MAU " + e.getMessage());
        }
        assert(workbook != null);
        System.out.printf("Succesfully read workbook with %i sheets", workbook.getNumberOfSheets());
    }
}
