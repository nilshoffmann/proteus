package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IUniqueObject;
import java.util.List;
import java.util.Map;

/**
 * Annotation of a molecule that can be part of a reaction.
 *
 * @author Konstantin Otte
 */
public interface ICompound extends IPropertyChangeSource, IUniqueObject {

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
