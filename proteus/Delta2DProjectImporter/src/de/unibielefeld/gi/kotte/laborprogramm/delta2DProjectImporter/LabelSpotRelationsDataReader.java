package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads in gelImages/labelSpotRelations/<gelId>.xml files from Delta2D projects.
 * 
 * @author Konstantin Otte
 */
public class LabelSpotRelationsDataReader {
    public Map<String, String> parseLabelSpotRelations (File f) {
        Map<String, String> labelSpotRelations = new HashMap<String, String>();
        
        Pattern labelSpotPattern = Pattern.compile("<isInside label_id=\"(\\d+)\" spot_id=\"(\\d+)\"/>");
        try {
            BufferedReader input = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = input.readLine()) != null){
                Matcher labelSpotMatcher = labelSpotPattern.matcher(line);
                while(labelSpotMatcher.find()) {
                    labelSpotRelations.put(labelSpotMatcher.group(1), labelSpotMatcher.group(2));
//                    System.out.println("Pattern found. Group 1: '" + labelSpotMatcher.group(1) +
//                            "', group 2: '" + labelSpotMatcher.group(2) + "'.");
                }
            }
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(LabelSpotRelationsDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return labelSpotRelations;
    }
}
