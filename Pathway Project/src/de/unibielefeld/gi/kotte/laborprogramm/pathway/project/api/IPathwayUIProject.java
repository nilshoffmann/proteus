package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPropertyChangeSource;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Collection;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectState;

/**
 * Netbeans Project for IPathwayProject.
 *
 * @author kotte
 */
public interface IPathwayUIProject extends Project, IPropertyChangeSource, PropertyChangeListener {

    public void activate(URL url);

    public void open();
    
    public void close();

    public void setProjectState(ProjectState ps);

    public <T> void store(T... t);
//
    public <T> Collection<T> retrieve(Class<T> c);
//
    public void delete(Object... obj);
    
    public IPathwayProject getProjectData();

    public void setProjectData(IPathwayProject project);
}
