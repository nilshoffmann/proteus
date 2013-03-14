package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.quantitationData.QuantitationData;
import java.io.File;

/**
 * Reads in gelImages/quantitations/<gelId>.xml files from Delta2D projects.
 * 
 * @author kotte
 */
public class QuantitationDataReader {
    public QuantitationData parseQuantification(File f) {
		return UnmarshalUtilities.unmarshal(QuantitationData.class, "de.unibielefeld.gi.kotte.laborprogramm.xml.quantitationData", "/com/decodon/dtd/delta2d/QuantitationData.dtd", f);
//        try {
//            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.quantitationData");
//			Unmarshaller u = jc.createUnmarshaller();
//			u.setSchema(null);
//            QuantitationData qd = (QuantitationData) u.unmarshal(new FileInputStream(f));
//            return qd;
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(QuantitationDataReader.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(QuantitationDataReader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
    }
}
