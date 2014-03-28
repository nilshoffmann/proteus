package de.unibielefeld.gi.kotte.laborprogramm.pathway.utils;

import de.unibielefeld.gi.omicsTools.biocyc.ptools.CommonName;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Compound;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Protein;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Strain;

/**
 * Provides static methods to extract names from domain objects.
 * 
 * @author Konstantin Otte
 */
public class NameTools {

    public static String getSpeciesName(PGDB pgdb) {
        StringBuilder builder = new StringBuilder((String) pgdb.getSpecies().getContent().iterator().next());
        Strain strain = pgdb.getStrain();
        if (strain != null) {
            builder.append(' ').append(strain.getContent());
        }
        return builder.toString();
    }

    public static String getPathwayName(Pathway pw) {
        for (Object obj : pw.getCitationOrCommentOrCommonName()) {
            if (obj instanceof CommonName) {
                return (new StringBuilder("<html>")).append(((CommonName) obj).getContent()).append("</html>").toString();
            }
        }
        return pw.getFrameid();
    }

    public static String getProteinName(Protein prot) {
        for (Object obj : prot.getCatalyzes().getEnzymaticReaction().iterator().next().getCitationOrCofactorOrComment()) {
            if (obj instanceof CommonName) {
                return (new StringBuilder("<html>")).append(((CommonName) obj).getContent()).append("</html>").toString();
            }
        }
        return prot.getFrameid();
    }

    public static String getCompoundName(Compound cmp) {
        for (Object obj : cmp.getAbbrevNameOrAppearsInLeftSideOfOrAppearsInRightSideOf()) {
            if (obj instanceof CommonName) {
                return (new StringBuilder("<html>")).append(((CommonName) obj).getContent()).append("</html>").toString();
            }
        }
        return cmp.getFrameid();
    }
}
