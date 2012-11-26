package de.unibielefeld.gi.kotte.laborprogramm.pathway.manager.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.cookies.IPathwayViewOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.sbml.PathwayExplorerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author kotte
 */
@ServiceProvider(service=IPathwayViewOpenCookie.class)
public class PathwayViewOpenCookie implements IPathwayViewOpenCookie {
    @Override
    public void open() {
        IPathwayProject project = Utilities.actionsGlobalContext().lookup(IPathwayProject.class);
        if(project != null) {
            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().openTopComponentFor(project,PathwayExplorerTopComponent.class);
        }
    }
}
