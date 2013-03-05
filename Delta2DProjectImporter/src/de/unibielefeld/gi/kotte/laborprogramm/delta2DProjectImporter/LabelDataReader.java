package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.labelData.LabelData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Reads in gelImages/labels/<gelId>.xml files from Delta2D projects.
 * 
 * @author kotte
 */
class LabelDataReader {
    public LabelData parseLabels(File f) {
        try {
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.labelData");
            Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(null);
            LabelData ld = (LabelData) u.unmarshal(new FileInputStream(f));
            return ld;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QuantitationDataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(QuantitationDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
