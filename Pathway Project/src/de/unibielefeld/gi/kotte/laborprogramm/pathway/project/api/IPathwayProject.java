package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IUniqueObject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMap;
import java.beans.PropertyChangeListener;
import org.netbeans.api.project.Project;
import org.sbml.jsbml.SBMLDocument;

/**
 * Project Datatype to handle Pathway Data.
 *
 * @author kotte
 */
public interface IPathwayProject extends Project, IPropertyChangeSource, PropertyChangeListener, IUniqueObject {

//    public void activate(URL url);
//
//    public void close();
//    
//    public void setProjectState(ProjectState ps);
//
//    public <T> void store(T... t);
//
//    public <T> Collection<T> retrieve(Class<T> c);
//
//    public void delete(Object... obj);
    
    public String getName();
    
    public void setName(String name);
    
    public SBMLDocument getDocument();
    
    public void setDocument(SBMLDocument document);

    public IPathwayMap getPathwayMap();

    public void setPathwayMap(IPathwayMap pathwayMap);
}
