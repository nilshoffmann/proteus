package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelData;
import java.io.File;

/**
 * Reads in gelImages.xml files from Delta2D projects.
 *
 * @author Konstantin Otte
 */
public class GelDataReader {

    public GelData getGelData(File f) {
		
		return UnmarshalUtilities.unmarshal(GelData.class, "de.unibielefeld.gi.kotte.laborprogramm.xml.gelData", "/com/decodon/dtd/delta2d/GelData.dtd", f);
//		
//        try {
//            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.gelData");
//            Unmarshaller u = jc.createUnmarshaller();
//			u.setSchema(null);
//            GelData gd = (GelData) u.unmarshal(new FileInputStream(f));
//            return gd;
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(GelDataReader.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(GelDataReader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
    }
}
