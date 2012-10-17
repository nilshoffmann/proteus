package de.unibielefeld.gi.kotte.laborprogramm.pathways.sbml;

import de.unibielefeld.gi.omicsTools.biocyc.ptools.CommonName;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import java.util.List;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;

/**
 * Provides MetaCyc Pathway Information for SBML view.
 *
 * @author kotte
 */
public class PathwayController {
    
//    PGDB org;
    List<Pathway> pathways;
    
    public PathwayController(/*PGDB org,*/ List<Pathway> pathways){
//        this.org = org;
        this.pathways = pathways;
    }
    
    public String getPathwayName(Species species) {
        Pathway pathway = getPathway(species);
        if (pathway != null) {
            for (Object obj : pathway.getCitationOrCommentOrCommonName()) {
                if (obj instanceof CommonName) {
                    return (new StringBuilder("<html>")).append(((CommonName) obj).getContent()).append("</html>").toString();
                }
            }
        }
        return "no pathway specified";
    }

    public String getPathwayId(Species species) {
        Pathway pathway = getPathway(species);
        if (pathway != null) {
            return pathway.getFrameid();
        }
        return "";
    }

    public String getPathwayName(Reaction reaction) {
        Pathway pathway = getPathway(reaction);
        if (pathway != null) {
            for (Object obj : pathway.getCitationOrCommentOrCommonName()) {
                if (obj instanceof CommonName) {
                    return (new StringBuilder("<html>")).append(((CommonName) obj).getContent()).append("</html>").toString();
                }
            }
        }
        return "no pathway specified";
    }
    
    public String getPathwayId(Reaction reaction) {
        Pathway pathway = getPathway(reaction);
        if (pathway != null) {
            return pathway.getFrameid();
        }
        return "";
    }
    
    private Pathway getPathway(Species species) {
        if(pathways.isEmpty()){
            return null;
        } else { //TODO look for fitting pathway for species
            return pathways.get(0);
        }
    }
    
    private Pathway getPathway(Reaction reaction) {
        if(pathways.isEmpty()){
            return null;
        } else { //TODO look for fitting pathway for reaction
            return pathways.get(0);
        }
    }
}
