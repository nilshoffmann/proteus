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
        File f = new File("gelImages.xml");
        f.deleteOnExit();
        String path = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/gelImages.xml";
        ResourceHandler.writeResourceToDisk(path, f);
        GelDataReader gdr = new GelDataReader();
        List<IGel> gels = gdr.getGels(f);
        for (IGel gel : gels) {
            System.out.println(gel);
        }
    }
}
