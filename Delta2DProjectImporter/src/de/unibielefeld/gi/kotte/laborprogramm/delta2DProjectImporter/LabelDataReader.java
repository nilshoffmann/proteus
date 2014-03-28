package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.labelData.LabelData;
import java.io.File;

/**
 * Reads in gelImages/labels/<gelId>.xml files from Delta2D projects.
 * 
 * @author Konstantin Otte
 */
class LabelDataReader {
    public LabelData parseLabels(File f) {
		return UnmarshalUtilities.unmarshal(LabelData.class, "de.unibielefeld.gi.kotte.laborprogramm.xml.labelData", "/com/decodon/dtd/delta2d/LabelData.dtd", f);
//        try {
//            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.labelData");
//            Unmarshaller u = jc.createUnmarshaller();
//			u.setSchema(null);
//            LabelData ld = (LabelData) u.unmarshal(new FileInputStream(f));
//            return ld;
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(QuantitationDataReader.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(QuantitationDataReader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
    }
}
