package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGelFactory;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelImage;
import java.io.File;
import javax.swing.JFileChooser;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class GelDataReaderTest {

    private final static boolean FILECHOOSER = true;

    public static void main(String[] args) {
        File f = null;
        if (!FILECHOOSER) {
            f = new File("Export_1.xlsx");
            f.deleteOnExit();
            String path = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/gelImages.xml";
            ResourceHandler.writeResourceToDisk(path, f);
        } else {
            JFileChooser jfc = new JFileChooser();
            int status = jfc.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                f = jfc.getSelectedFile();
            }
        }
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
