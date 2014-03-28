package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProjectFactory;

/**
 * Factory for creating proteomic projects.
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = IProjectFactory.class)
public class ProjectFactory implements IProjectFactory {

    @Override
    public IProject createEmptyProject() {
        return new Project();
    }
}
