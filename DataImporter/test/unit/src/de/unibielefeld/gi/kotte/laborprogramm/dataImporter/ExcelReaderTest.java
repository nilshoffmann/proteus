package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProjectFactory;
import java.io.File;
import java.util.Arrays;
import javax.swing.JFileChooser;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class ExcelReaderTest {

    private final static boolean FILECHOOSER = false;

    public static void main(String[] args) {
        File f = null;
        if (!FILECHOOSER) {
            f = new File("Export_1.xlsx");
            f.deleteOnExit();
            String path = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/Export_1.xlsx";
            ResourceHandler.writeResourceToDisk(path, f);
        } else {
            JFileChooser jfc = new JFileChooser();
            int status = jfc.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                f = jfc.getSelectedFile();
            }
        }
        ExcelReader er = new ExcelReader();
        IProject project = Lookup.getDefault().lookup(IProjectFactory.class).createEmptyProject();
        er.parseExport(f, Arrays.asList(project));
        System.out.println(project);
    }
}
