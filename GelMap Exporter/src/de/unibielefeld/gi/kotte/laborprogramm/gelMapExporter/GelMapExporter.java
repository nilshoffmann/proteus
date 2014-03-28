package de.unibielefeld.gi.kotte.laborprogramm.gelMapExporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openide.util.Exceptions;

/**
 * Exporter for IGels in GelMap format.
 *
 * @author Konstantin Otte
 */
public class GelMapExporter {

    public static void export(File outFile, IGel gel) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            //GelMap Data Format description (http://www.gelmap.de/howto) included in the comments below
            /* The first line includes your custom headers. These are the labels for
             * the data shown in tooltips and the protein/peptide tables. */
            StringBuilder customHeader = new StringBuilder();
            customHeader.append("Spot ID").append('\t'); //ID
            customHeader.append("X").append('\t'); //X
            customHeader.append("Y").append('\t'); //Y
            customHeader.append("Protein Name").append('\t'); //NAME
            customHeader.append("Accession").append('\t'); //ACC
            customHeader.append("EC Number").append('\t'); //EC
            customHeader.append("Abbreviation").append('\t'); //ABBR
            customHeader.append("Volume").append('\t'); //VOL
            customHeader.append("MS Coverage").append('\t'); //COV
            customHeader.append("Score").append('\t'); //SCR
            customHeader.append("pI Value").append('\t'); //PI
            customHeader.append("Mol. Weight"); //MW
            bw.write(customHeader.toString());
            bw.newLine();
            /* The second line includes the system headers. You can mark special columns like
             * the X,Y or TITLE columns which must be processed differently by the system. */
            StringBuilder systemHeader = new StringBuilder();
            systemHeader.append("ID").append('\t'); //ID
            systemHeader.append("X").append('\t'); //X
            systemHeader.append("Y").append('\t'); //Y
            systemHeader.append("TITLE").append('\t'); //NAME
            systemHeader.append("ACC").append('\t'); //ACC
            systemHeader.append("CLASS").append('\t'); //EC
            systemHeader.append('\t'); //ABBR
            systemHeader.append('\t'); //VOL
            systemHeader.append('\t'); //COV
            systemHeader.append("SCORE").append('\t'); //SCR
            systemHeader.append('\t'); //PI
            ; //MW
            bw.write(systemHeader.toString());
            bw.newLine();
            /* The third line is reserved for a short description which will
             * be displayed as a tooltip in the protein/peptide tables. */
            StringBuilder shortDescription = new StringBuilder();
            shortDescription.append("Spot ID given by Delta2D").append('\t'); //ID
            shortDescription.append("Horizontal Spot Position").append('\t'); //X
            shortDescription.append("Vertical Spot Position").append('\t'); //Y
            shortDescription.append("Full Name of the Identification").append('\t'); //NAME
            shortDescription.append("Accession Number").append('\t'); //ACC
            shortDescription.append("EC Number").append('\t'); //EC
            shortDescription.append("Gene Abbreviation").append('\t'); //ABBR
            shortDescription.append("Normalized Spot Volume").append('\t'); //VOL
            shortDescription.append("Coverage value for MASCOT Identification").append('\t'); //COV
            shortDescription.append("MASCOT Score").append('\t'); //SCR
            shortDescription.append("Isoelectric point").append('\t'); //PI
            shortDescription.append("Protein Molecular Weight"); //MW
            bw.write(shortDescription.toString());
            bw.newLine();
            /* With the fourth line, you can configure which data to show in
             * the spot-popup. Leave cells empty if you want to hide the
             * information and fill in "show" to show this category. */
            StringBuilder showOptions = new StringBuilder();
            showOptions.append('\t'); //ID
            showOptions.append('\t'); //X
            showOptions.append('\t'); //Y
            showOptions.append("show").append('\t'); //NAME
            showOptions.append("show").append('\t'); //ACC
            showOptions.append("show").append('\t'); //EC
            showOptions.append('\t'); //ABBR
            showOptions.append('\t'); //VOL
            showOptions.append('\t'); //COV
            showOptions.append("show").append('\t'); //SCR
            showOptions.append('\t'); //PI
            ; //MW
            bw.write(showOptions.toString());
            bw.newLine();
            /* The fifth line is used for the filter categories on the right side.
             * A "1" marks the column that will be the 1st degree (root-level) filter.
             * A "2" marks the 2nd level filter and a "3" the most detailled filter. */
            StringBuilder filterCategories = new StringBuilder();
            filterCategories.append('\t'); //ID
            filterCategories.append('\t'); //X
            filterCategories.append('\t'); //Y
            filterCategories.append('\t'); //NAME
            filterCategories.append('\t'); //ACC
            filterCategories.append('\t'); //EC
            filterCategories.append('\t'); //ABBR
            filterCategories.append('\t'); //VOL
            filterCategories.append('\t'); //COV
            filterCategories.append('\t'); //SCR
            filterCategories.append('\t'); //PI
            ; //MW
            bw.write(filterCategories.toString());
            bw.newLine();
            for (ISpot spot : gel.getSpots()) {
                if (spot.getStatus() == SpotStatus.PICKED) {
                    IWell96 well96 = spot.getWell();
                    if (well96.getStatus() == Well96Status.PROCESSED) {
                        for (IWell384 well384 : well96.get384Wells()) {
                            for (IIdentificationMethod method : well384.getIdentification().getMethods()) {
                                for (IIdentification ident : method.getIdentifications()) {
                                    if (ident.getScore() > 0) {
                                        /* All subsequent lines are treated as data lines. */
                                        StringBuilder dataEntry = new StringBuilder();
                                        dataEntry.append(spot.getNumber()).append('\t'); //ID
                                        dataEntry.append(spot.getPosX()).append('\t'); //X
                                        dataEntry.append(spot.getPosY()).append('\t'); //Y
                                        dataEntry.append(ident.getName()).append('\t'); //NAME
                                        dataEntry.append(ident.getAccession()).append('\t'); //ACC
                                        List<String> ecNumbers = ident.getEcNumbers();
                                        if (!ecNumbers.isEmpty()) {
                                            dataEntry.append(ecNumbers.iterator().next()); //EC
                                        }
                                        dataEntry.append('\t'); //EC
                                        dataEntry.append(ident.getAbbreviation()).append('\t'); //ABBR
                                        dataEntry.append(spot.getNormVolume()).append('\t'); //VOL
                                        dataEntry.append(ident.getCoverage()).append('\t'); //COV
                                        dataEntry.append(ident.getScore()).append('\t'); //SCR
                                        dataEntry.append(ident.getPiValue()).append('\t'); //PI
                                        dataEntry.append(ident.getProteinMolecularWeight()); //MW
                                        bw.write(dataEntry.toString());
                                        bw.newLine();
                                    }
                                } //for ident
                            } //for method
                        } //for well384
                    } // if well96 is PROCESSED
                } // if spot is PICKED
            } // for spot
            //finish output
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
