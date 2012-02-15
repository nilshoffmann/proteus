package de.unibielefeld.gi.kotte.laborprogramm.BTRImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
            String[] header = in.readLine().split("\t"); //FIXME header Zeile
            in.readLine(); //FIXME Sternchen Zeile

            //Pattern definition (re-usable)
            Pattern abbreviationPattern = Pattern.compile("^(\\w{3,4}) ");
            Pattern namePattern = Pattern.compile("([\\w/\\- ]*)");
            Pattern gendbPattern = Pattern.compile("\\(GenDB-ID=(\\d+)\\)");
            Pattern gendbProjectPattern = Pattern.compile("\\(GenDB-Project=(\\d+)\\)");
            Pattern keggPattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.[\\d\\-]+)");
            IWell384 well = null;
            IIdentificationMethod method = null;
            while ((line = in.readLine()) != null) {
                //line = line.trim();
                System.out.println(line);
                //create identification
                IIdentification identification = Lookup.getDefault().lookup(IIdentificationFactory.class).createIdentification();
                String[] data = line.split("\t");
//                data = line.split("\t");
                System.out.println("Parsing line: "+Arrays.toString(data));
                for (int i = 0; i < data.length; i++) {
                    BtrColumn column = BtrColumn.normalizeColumnName(header[i]);
                    //TODO data[4] gibt den Status an (Identified/Undefined/Error)
                    System.out.println("Parsing column: "+column+" with value: "+data[i]);
                    switch (column) {
                        case POS_ON_SCOUT:
                            //only assign well, if entry is non empty, otherwise we are processing a previously identified well
                            if(!data[i].trim().isEmpty()) {
                                String[] coordinates = data[i].split(":");
                                if(coordinates.length==2) {
                                    well = plate.getWell(coordinates[0].charAt(0), Integer.parseInt(coordinates[1]));
                                }
                            }
                            break;
                        case ACCESSION:
                            String accession = data[1];
                            // "lcl|" am Start rausschmeissen
                            if(accession.startsWith("lcl|")) {
                                accession = accession.substring(4);
                            }
                            identification.setAccession(accession);
                            break;
                        case DIFFERENCE:
                            identification.setDifference(Integer.parseInt(data[i]));
                            break;
                        case MASCOT_SCORE:
                            identification.setScore(Float.parseFloat(data[i]));
                            break;
                        case METHOD:
                            //read in method, if there is a new one
                            if(!data[i].trim().isEmpty()) {
                                method = well.getIdentification().getMethodByName(data[i]);
                            }
                            break;
                        case MS_COVERAGE:
                            identification.setCoverage(Integer.parseInt(data[i]));
                            break;
                        case PI_VALUE:
                            identification.setPiValue(Float.parseFloat(data[i]));
                            break;
                        case PROTEIN_MW:
                            identification.setProteinMolecularWeight(Float.parseFloat(data[i]));
                            break;
                        case STATUS:
                            break;
                        case TITLE:
                            //parse data
                            String abbreviation = "";
                            Matcher abbreviationMatcher = abbreviationPattern.matcher(data[2].replaceAll("\"",""));
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

                            String genDbProject = "";
                            Matcher genDbProjectMatcher = gendbProjectPattern.matcher(data[2]);
                            if (genDbProjectMatcher.find()) {
                                genDbProject = genDbProjectMatcher.group();
                            }
                            List<String> keggNumbers = new ArrayList<String>();
                            Matcher keggMatcher = keggPattern.matcher(data[2]);
                            while (keggMatcher.find()) {
                                keggNumbers.add(keggMatcher.group(1));
                            }

                            //clean up name
                            if(!abbreviation.isEmpty()) {
                                //name.replaceFirst(abbreviation, "");
                                name = name.substring(abbreviation.length());
                            }
                            if(!keggNumbers.isEmpty()) {
                                name = name.substring(0, name.length()-2);
                            }
                            name = name.trim();

                            //set values parsed from name
                            identification.setAbbreviation(abbreviation);
                            identification.setName(name);
                            identification.setGendbId(gendbId);
                            identification.setGendbProject(genDbProject);
                            identification.setKeggNumbers(keggNumbers);
                            break;
                        default:
                    }
                }
                //set method
                identification.setMethod(method);
                identification.setSource(f.getCanonicalPath());
                //add identification to well
                System.out.println(identification);
                if (well != null && well.getStatus() != Well384Status.EMPTY || well.getStatus() == Well384Status.ERROR) {
                    well.getIdentification().getMethodByName(method.getName()).addIdentification(identification);
                } else {
                    System.out.println("Can not add identification data to: " + well);
                    //TODO Fehler behandeln
                }
            }
            checkStatus(plate);
        } catch (IOException ex) {
            Logger.getLogger(BTRReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void checkStatus(IPlate384 plate) {
        for (IWell384 well : plate.getWells()) {
            if (well.getStatus() == Well384Status.FILLED) {
                List<IIdentificationMethod> methods = well.getIdentification().getMethods();
                if (methods.isEmpty()) {
                    well.setStatus(Well384Status.UNIDENTIFIED);
                } else {
                    //TODO: check if there is a valid identification (-> status!)
                    well.setStatus(Well384Status.IDENTIFIED);
                }
            }
        }
    }
}
