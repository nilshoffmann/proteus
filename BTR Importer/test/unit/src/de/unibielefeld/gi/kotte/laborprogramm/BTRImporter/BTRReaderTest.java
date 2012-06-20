package de.unibielefeld.gi.kotte.laborprogramm.BTRImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384Factory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import java.io.File;
import javax.swing.JFileChooser;
import org.openide.util.Lookup;

/**
 * Tests the FileReader.
 *
 * @author kotte
 */
public class BTRReaderTest {

    private final static boolean FILECHOOSER = false;//true;

    public static void main(String[] args) {
        File f = null;
        if (!FILECHOOSER) {
            f = new File("Export_1.xlsx");
            f.deleteOnExit();
            String path = "/de/unibielefeld/gi/kotte/laborprogramm/BTRImporter/resources/0209513.0011326.0000000552.BTR";
            ResourceHandler.writeResourceToDisk(path, f);
        } else {
            JFileChooser jfc = new JFileChooser();
            int status = jfc.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                f = jfc.getSelectedFile();
            }
        }

        //create new (filled!) plate for the test run
        IPlate384 plate = Lookup.getDefault().lookup(IPlate384Factory.class).createPlate384();
        for(IWell384 well: plate.getWells()) {
            well.setStatus(Well384Status.FILLED);
        }

        BTRReader.readBTRFile(f, plate);
        //System.out.println(plate.toFullyRecursiveString());
        
        //TEST
        for (IIdentificationMethod method : plate.getWell('A', 19).getIdentification().getMethods()) {
            System.out.println(method.getName());
        }
    }
}