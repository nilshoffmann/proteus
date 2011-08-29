package de.unibielefeld.gi.kotte.laborprogramm.BTRImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

            //Pattern definition (re-usable)
            Pattern abbreviationPattern = Pattern.compile("^(\\w{3,4}) ");
            Pattern namePattern = Pattern.compile("(.*)");//TODO sinnvolle Loesung...
            Pattern gendbPattern = Pattern.compile("\\(GenDB-ID=(\\d+)\\)");
            Pattern keggPattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)");

            while ((line = in.readLine()) != null) {
                line = line.trim();
                System.out.println(line);
                String[] data = new String[10];
                data = line.split("\t");

                //TODO data[4] gibt den Status an (Identified/Undefined/Error)
                //TODO im Augenblick wird von exakt einer Identification pro WellIdentification ausgegangen

                //parse data
                String abbreviation = "";
                Matcher abbreviationMatcher = abbreviationPattern.matcher(data[2]);
                if (abbreviationMatcher.find()) {
                    abbreviation = abbreviationMatcher.group(1);
                    abbreviation = Character.toUpperCase(abbreviation.charAt(0)) + abbreviation.substring(1, abbreviation.length());
                }
                String name = "";
                Matcher nameMatcher = namePattern.matcher(data[2]);
                if (nameMatcher.find()) {
                    name = nameMatcher.group(1);
                }
                int gendbId = -1;
                Matcher gendbMatcher = gendbPattern.matcher(data[2]);
                if (gendbMatcher.find()) {
                    gendbId = Integer.parseInt(gendbMatcher.group(1));
                }
                List<String> keggNumbers = new ArrayList<String>();
                Matcher keggMatcher = keggPattern.matcher(data[2]);
                while (keggMatcher.find()) {
                    keggNumbers.add(keggMatcher.group(1));
                }

                //create identification
                IIdentification identification = Lookup.getDefault().lookup(IIdentificationFactory.class).createIdentification();
                identification.setAbbreviation(abbreviation);
                identification.setAccession(data[1]);
                identification.setName(name);
                identification.setCoverage(Integer.parseInt(data[7]));
                identification.setDifference(Integer.parseInt(data[8]));
                identification.setGendbId(gendbId);
                identification.setKeggNumbers(keggNumbers);
                identification.setPiValue(Float.parseFloat(data[6]));
                identification.setProteinMolecularWeight(Float.parseFloat(data[5]));
                identification.setScore(Float.parseFloat(data[3]));
                identification.setMethod(data[9]);
                System.out.println(identification);

                //add identification to well
                IWell384 well = plate.getWell(data[0].charAt(0), Integer.parseInt(data[0].substring(2)));
                //TODO check if well is processed
                IWellIdentification wellIdentification = well.getIdentification();
                wellIdentification.addIdentification(identification);
            }
        } catch (IOException ex) {
            Logger.getLogger(BTRReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
