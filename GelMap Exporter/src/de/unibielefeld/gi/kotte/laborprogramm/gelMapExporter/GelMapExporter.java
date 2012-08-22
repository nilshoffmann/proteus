package de.unibielefeld.gi.kotte.laborprogramm.gelMapExporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openide.util.Exceptions;

/**
 * Exporter for IProjects in GelMap format.
 *
 * @author kotte
 */
public class GelMapExporter {

    public static void export(File outFile, IGel gel) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            //GelMap Data Format description (http://www.gelmap.de/howto) included in the comments below
            //The first line includes your custom headers. These are the labels for the data shown in tooltips and the protein/peptide tables.
            StringBuilder customHeader = new StringBuilder();
            bw.write(customHeader.toString());
            bw.newLine();
            //The second line includes the system headers. You can mark special columns like the X,Y or TITLE columns which must be processed differently by the system.
            StringBuilder systemHeader = new StringBuilder();
            systemHeader.append("ID").append('\t').append('X').append('\t').append('Y').append('\t');
            systemHeader.append("TITLE").append('\t').append("ACC").append('\t').append("ACCSRC").append('\t');
            systemHeader.append("CLASS").append('\t').append("REGZS").append('\t').append("REGSZ").append('\t');
            systemHeader.append("ORIGIN").append('\t').append("SCORE");
            bw.write(systemHeader.toString());
            bw.newLine();
            //The third line is reserved for a short description which will be displayed as a tooltip in the protein/peptide tables.
            StringBuilder shortDescription = new StringBuilder();
            bw.write(shortDescription.toString());
            bw.newLine();
            //With the fourth line, you can configure which data to show in the spot-popup. Leave cells empty if you want to hide the information and fill in "show" to show this category.
            StringBuilder showOptions = new StringBuilder();
            showOptions.append("show");
            for (int i = 0; i < 10; i++) {
                showOptions.append('\t').append("show");
            }
            bw.write(showOptions.toString());
            bw.newLine();
            //The fifth line is used for the filter categories on the right side. A "1" marks the column that will be the 1st degree (root-level) filter. A "2" marks the 2nd level filter and a "3" the most detailled filter.
            StringBuilder filterCategories = new StringBuilder();
            filterCategories.append('\t').append('\t').append('\t').append('\t').append('\t').append('\t').append('\t');
            filterCategories.append('3').append('\t').append('2').append('\t').append('\t').append('1');
            bw.write(filterCategories.toString());
            bw.newLine();
            //All subsequent lines are treated as data lines.
            for (ISpot spot : gel.getSpots()) {
                StringBuilder dataEntry = new StringBuilder();
                dataEntry.append(spot.getNumber()).append('\t'); //ID
                dataEntry.append(spot.getPosX()).append('\t'); //X
                dataEntry.append(spot.getPosY()).append('\t'); //Y
                dataEntry.append(spot.getLabel()).append('\t'); //TITLE
                dataEntry.append(retrieveSpotAccession(spot)).append('\t'); //ACC
                dataEntry.append('\t'); //ACCSRC
                dataEntry.append(retrieveSpotECNumber(spot)).append('\t'); //CLASS
                dataEntry.append(spot.getGreyVolume()).append('\t'); //REGZS
                dataEntry.append(spot.getNormVolume()).append('\t'); //REGSZ
                dataEntry.append("unknown").append('\t'); //ORIGIN
                dataEntry.append('0').append('\t'); //SCORE
                bw.write(dataEntry.toString());
                bw.newLine();
            }
            //finish output
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    private static String retrieveSpotAccession(ISpot spot) {
        return "";
    }
    
    private static String retrieveSpotECNumber(ISpot spot) {
        return "";
    }
}
