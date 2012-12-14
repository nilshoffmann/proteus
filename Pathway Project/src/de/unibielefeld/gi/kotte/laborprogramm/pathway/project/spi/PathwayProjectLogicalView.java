package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayUIProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.nodes.PathwayProjectNode;
import java.beans.IntrospectionException;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.nodes.Node;

/**
 * LogicalViewProvider for IPathwayProjects.
 *
 * @author kotte
 */
public class PathwayProjectLogicalView implements LogicalViewProvider {

    private final IPathwayUIProject project;

    public PathwayProjectLogicalView(IPathwayUIProject project) {
        this.project = project;
    }

    @Override
    public Node createLogicalView() {
        try {
            return new PathwayProjectNode(project);
        } catch (IntrospectionException ex) {
            return Node.EMPTY;
        }
    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
}
