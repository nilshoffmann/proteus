package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author kotte
 */
public class GelDataReaderTest {

    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showOpenDialog(null);
        if(status == JFileChooser.APPROVE_OPTION) {
            File f= jfc.getSelectedFile();
            GelDataReaderTest gdr = new GelDataReaderTest();
            gdr.getGels(f);
        }
    }

    public List<IGel> getGels(File f) {
        try {
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.gelData");
            Unmarshaller u = jc.createUnmarshaller();
            GelData gd = (GelData)u.unmarshal(
                    new FileInputStream(f));
            System.out.println(gd);
            for(GelImage gi : gd.getGelImages().getGelImage()) {
                System.out.println("Name: "+gi.getName()+" source image: "+gi.getSourceImage());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GelDataReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(GelDataReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.emptyList();
    }
}
