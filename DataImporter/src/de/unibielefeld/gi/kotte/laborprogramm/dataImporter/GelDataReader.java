package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGelFactory;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.openide.util.Lookup;

/**
 * Reads in gelImages.xml files from Delta2D Export.
 *
 * @author kotte
 */
public class GelDataReader {
    public List<IGel> getGels(File f) {
        try {
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.gelData");
            Unmarshaller u = jc.createUnmarshaller();
            GelData gd = (GelData)u.unmarshal(
                    new FileInputStream(f));
            System.out.println(gd);
            List<IGel> gels = new ArrayList<IGel>();
            for(GelImage gi : gd.getGelImages().getGelImage()) {
                //System.out.println("Name: "+gi.getName()+" source image: "+gi.getSourceImage());
                IGel gel = Lookup.getDefault().lookup(IGelFactory.class).createGel();
                gel.setName(gi.getName());
                gel.setFilename(gi.getSourceImage());
                gels.add(gel);
            }
            return gels;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GelDataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(GelDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.emptyList();
    }
}
