package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPropertyChangeSource;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Collection;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectState;

/**
 *
 * @author kotte
 */
public interface IPathwayProject extends Project, IPropertyChangeSource, PropertyChangeListener {

    public void activate(URL url);

    public void close();
    
    public void setProjectState(ProjectState ps);

    public <T> void store(T... t);

    public <T> Collection<T> retrieve(Class<T> c);

    public void delete(Object... obj);
}
