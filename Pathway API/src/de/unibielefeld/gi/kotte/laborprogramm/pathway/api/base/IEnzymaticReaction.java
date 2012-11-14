package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IUniqueObject;
import java.util.List;
import java.util.Map;

/**
 * Annotation of a reaction between molecules.
 *
 * @author kotte
 */
public interface IEnzymaticReaction extends IPropertyChangeSource, IUniqueObject {

    public String getCompartment();

    public void setCompartment(String compartment);

    public Map<EnzymaticReactionID, String> getIdMap();

    public String getId(EnzymaticReactionID type);

    public boolean hasId(EnzymaticReactionID type);

    public void addId(EnzymaticReactionID type, String str);

    public List<IPathway> getPathways();

    public void addPathway(IPathway pathway);

    public void setPathways(List<IPathway> pathways);
}
