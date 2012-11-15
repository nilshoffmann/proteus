package de.unibielefeld.gi.kotte.laborprogramm.pathway.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;

public class PathwayMapper {

//        List<String> ecNumbers = getECNumbersFromNotes(reaction);
//        if (!ecNumbers.isEmpty()) {
//            Iterator<String> it = ecNumbers.iterator();
//            StringBuilder sb = new StringBuilder();
//            sb.append(it.next());
//            while (it.hasNext()) {
//                sb.append(", ").append(it.next());
//            }
//            IReactionAnnotation reactionAnnotation = Lookup.getDefault().lookup(IReactionAnnotationFactory.class).createReactionAnnotation();
//            reactionAnnotation.addId(ReactionID.EC, sb.toString());
//        }
    public static String getKeggIDFromAnnotation(Reaction reaction) {
        String nonRDFannotation = reaction.getAnnotation().getNonRDFannotation();
        Pattern keggPattern = Pattern.compile("<celldesigner:proteinReference>(pr\\d+)</celldesigner:proteinReference>");
        Matcher keggMatcher = keggPattern.matcher(nonRDFannotation);
        String keggID = null;
        while (keggMatcher.find()) {
            keggID = keggMatcher.group(1);
        }
        return keggID;
    }

    public static String getKeggIDFromAnnotation(Species species) {
        String nonRDFannotation = species.getAnnotation().getNonRDFannotation();
        Pattern keggPattern = Pattern.compile("<celldesigner:name>(c\\d+)</celldesigner:name>");
        Matcher keggMatcher = keggPattern.matcher(nonRDFannotation);
        String keggID = null;
        while (keggMatcher.find()) {
            keggID = keggMatcher.group(1);
        }
        return keggID;
    }

    public static List<String> getECNumbersFromNotes(Reaction reaction) {
        String notes = reaction.getNotesString();
        Pattern ecPattern = Pattern.compile("<html:p>EC Number: (.+)</html:p>");
//        Pattern ecPattern = Pattern.compile("<html:p>EC Number: (\\d+\\.\\d+\\.\\d+\\.\\d+)</html:p>");
        Matcher ecMatcher = ecPattern.matcher(notes);
        List<String> ecNumbers = new ArrayList<String>();
        while (ecMatcher.find()) {
            ecNumbers.add(ecMatcher.group(1));
        }
        return ecNumbers;
    }
}
