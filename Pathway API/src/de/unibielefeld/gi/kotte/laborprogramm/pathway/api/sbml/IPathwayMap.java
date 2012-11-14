package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IUniqueObject;
import org.sbml.jsbml.SBMLDocument;

public interface IPathwayMap extends IPropertyChangeSource, IUniqueObject {

    public SBMLDocument getDocument();

    public void setDocument(SBMLDocument document);

    public String getOrganismID();

    public void setOrganismID(String organismID);

    public String getOrganismName();

    public void setOrganismName(String organismName);
}
