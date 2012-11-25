package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayProjectFactory;
import org.openide.util.lookup.ServiceProvider;

/**
 * Default implementation of IPathwayProjectFactory.
 *
 * @author kotte
 */
@ServiceProvider(service = IPathwayProjectFactory.class)
public class PathwayProjectFactory implements IPathwayProjectFactory {

    @Override
    public IPathwayProject createProject(String name) {
        PathwayProject result = new PathwayProject();
        result.setName(name);
        return result;
    }
}
