package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.nodes.PathwayProjectNode;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 * LogicalViewProvider for IPathwayProjects.
 *
 * @author kotte
 */
public class PathwayProjectLogicalView implements LogicalViewProvider {

    private final IPathwayProject project;

    public PathwayProjectLogicalView(IPathwayProject project) {
        this.project = project;
    }

    @Override
    public Node createLogicalView() {
        return new PathwayProjectNode(project, Lookups.fixed(project));
    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
}
