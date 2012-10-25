package de.unibielefeld.gi.kotte.laborprogramm.pathway.api;

import java.util.List;
import java.util.Map;

/**
 * Annotation of a reaction between molecules.
 *
 * @author kotte
 */
public interface IReactionAnnotation extends IPropertyChangeSource {
    public String getCompartment();
    public void setCompartment(String compartment);
    
    public Map<ReactionID, String> getIdMap();
    public String getId(ReactionID type);
    public boolean hasId(ReactionID type);
    public void addId(ReactionID type, String str);
    
    public List<IPathway> getPathways();
    public void addPathway(IPathway pathway);
    public void setPathways(List<IPathway> pathways);
}
