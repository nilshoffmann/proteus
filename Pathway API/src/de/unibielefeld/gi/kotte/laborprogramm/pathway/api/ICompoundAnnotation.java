package de.unibielefeld.gi.kotte.laborprogramm.pathway.api;

import java.util.List;
import java.util.Map;

/**
 * Annotation of a molecule that can be part of a reaction.
 *
 * @author kotte
 */
public interface ICompoundAnnotation extends IPropertyChangeSource {
    public String getCompartment();
    public void setCompartment(String compartment);
    
    public Map<CompoundID, String> getIdMap();
    public String getId(CompoundID type);
    public boolean hasId(CompoundID type);
    public void addId(CompoundID type, String str);
    
    public List<IPathway> getPathways();
    public void addPathway(IPathway pathway);
    public void setPathways(List<IPathway> pathways);
}
