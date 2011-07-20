package de.unibielefeld.gi.kotte.laborprogramm.project.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import java.beans.PropertyChangeListener;
import java.net.URL;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectState;

/**
 * Interface for Proteomic Projects.
 *
 * @author kotte
 */
public interface IProteomicProject extends Project, IPropertyChangeSource, PropertyChangeListener {

    public void activate(URL url);

    public void close();

    public void setProjectData(IProject project);

    public void setProjectState(ProjectState ps);
    
}
