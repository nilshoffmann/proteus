package de.unibielefeld.gi.kotte.laborprogramm.pathway.api;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMap;
import org.sbml.jsbml.SBMLDocument;

/**
 * Project datastructure for SBML View Projects.
 *
 * @author Konstantin Otte
 */
public interface IPathwayProject extends IPropertyChangeSource, IUniqueObject {

    public String getName();

    public void setName(String name);

    public SBMLDocument getDocument();

    public void setDocument(SBMLDocument document);

    public IPathwayMap getPathwayMap();

    public void setPathwayMap(IPathwayMap pathwayMap);
}
