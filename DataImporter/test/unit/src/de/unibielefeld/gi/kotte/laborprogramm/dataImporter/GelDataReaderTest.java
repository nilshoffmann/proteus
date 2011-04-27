package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.resourceHandler.ResourceHandler;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.io.File;
import java.util.List;

/**
 *
 * @author kotte
 */
public class GelDataReaderTest {

    public static void main(String[] args) {
//        JFileChooser jfc = new JFileChooser();
//        int status = jfc.showOpenDialog(null);
//        if(status == JFileChooser.APPROVE_OPTION) {
//            File f= jfc.getSelectedFile();
            File f = new File("gelImages.xml");
            //f.deleteOnExit();
            //BufferedWriter bos;
            ResourceHandler.writeResourceToDisk("/resources/gelImages.xml", f);
            GelDataReader gdr = new GelDataReader();
            List<IGel> gels = gdr.getGels(f);
            for (IGel gel : gels) {
                System.out.println(gel);
            }
//        }
    }
}
