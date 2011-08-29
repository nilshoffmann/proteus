package de.unibielefeld.gi.kotte.laborprogramm.BTRImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentificationFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Lookup;

/**
 * Reads in *.BTR files.
 *
 * @author kotte
 */
public class BTRReader {

    /**
     * Parses the informations from a *.BTR file and passes them to the 384 well plate object.
     *
     * @param f the *.BTR File to be read
     * @param plate the IPlate384 Object representing the plate.
     */
    public static void readBTRFile(File f, IPlate384 plate) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(f));
            String line = null;
            in.readLine(); //FIXME header Zeile
            in.readLine(); //FIXME Sternchen Zeile
            while ((line = in.readLine()) != null) {
                line = line.trim();
                System.out.println(line);
                String[] data = new String[10];
                data = line.split("\t");

                //TODO data[4] gibt den Status an (Identified/Undefined/Error)
                //TODO im Augenblick wird von exakt einer Identification pro WellIdentification ausgegangen

                IIdentification identification = Lookup.getDefault().lookup(IIdentificationFactory.class).createIdentification();
                identification.setMethod(data[9]);
                identification.setName(data[2]);
                identification.setId(data[1]);
                identification.setDescription(line);
                System.out.println(identification);

                IWellIdentification wellIdentification = Lookup.getDefault().lookup(IWellIdentificationFactory.class).createWellIdentification();
                wellIdentification.addIdentification(identification);
                wellIdentification.setUncertain(false); //TODO check if identification is uncertain

                IWell384 well = plate.getWell(data[0].charAt(0), Integer.parseInt(data[0].substring(2)));
                //TODO check if well is processed
                wellIdentification.setWell(well);
                well.setIdentification(wellIdentification);
            }
        } catch (IOException ex) {
            Logger.getLogger(BTRReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
