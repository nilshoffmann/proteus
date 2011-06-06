package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProjectFactory;
import java.io.File;
import org.openide.util.Lookup;

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
        IProject project = Lookup.getDefault().lookup(IProjectFactory.class).createEmptyProject();
        er.parseExport(f, project);
        System.out.println(project);
    }
}
