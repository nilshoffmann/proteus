package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.resourceHandler.ResourceHandler;
import java.io.File;

/**
 *
 * @author kotte
 */
public class ExcelReaderTest {

    public static void main(String[] args) {
        File f = new File("Export_1.xlsx");
        f.deleteOnExit();
        String path = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/Export_1.xlsx";
        ResourceHandler.writeResourceToDisk(path, f);
        ExcelReader er = new ExcelReader();
        er.parseExport(f);
    }
}
