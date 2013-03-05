package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Reads in gelImages.xml files from Delta2D projects.
 *
 * @author kotte
 */
public class GelDataReader {

    public GelData getGelData(File f) {
        try {
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.gelData");
            Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(null);
            GelData gd = (GelData) u.unmarshal(new FileInputStream(f));
            return gd;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GelDataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(GelDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
