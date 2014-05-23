package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProjectFactory;
import org.openide.util.lookup.ServiceProvider;

/**
 * Default implementation of IPathwayProjectFactory.
 *
 * @author Konstantin Otte
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
