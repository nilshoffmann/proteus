package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.quantitation.QuantitationData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Reads in gelImages/quantitations/<gelId>.xml files from Delta2D projects.
 * 
 * @author kotte
 */
public class QuantitationDataReader {
    public QuantitationData parseQuantification(File f) {
        try {
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.quantitation");
            Unmarshaller u = jc.createUnmarshaller();
            QuantitationData qd = (QuantitationData) u.unmarshal(new FileInputStream(f));
            return qd;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QuantitationDataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(QuantitationDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
