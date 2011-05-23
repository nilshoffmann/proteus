package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.resourceHandler.ResourceHandler;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGelFactory;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelImage;
import java.io.File;
import org.openide.util.Lookup;

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
        GelData gd = gdr.getGelData(f);
        for (GelImage gi : gd.getGelImages().getGelImage()) {
            //IGel gel = Lookup.getDefault().lookup(IGelFactory.class).createGel();
            //gel.setName(gi.getName());
            //gel.setFilename(gi.getSourceImage());
            //System.out.println(gel);

            System.out.println("name: " + gi.getName() + ", sourceImage: " + gi.getSourceImage() + ", gelImageID: " + gi.getGelImageId().getId());
        }
    }
}
