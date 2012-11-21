package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IUniqueObject;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import java.util.Collection;
import org.sbml.jsbml.SBMLDocument;

public interface IPathwayMap extends IPropertyChangeSource, IUniqueObject {

    public SBMLDocument getDocument();

    public void setDocument(SBMLDocument document);

    public String getOrganismID();

    public void setOrganismID(String organismID);

    public String getOrganismName();

    public void setOrganismName(String organismName);
    
    public void addPathway(Pathway pathway);
    
    public Pathway getPathway(String frameID);
    
    public Collection<Pathway> getPathways();
}
