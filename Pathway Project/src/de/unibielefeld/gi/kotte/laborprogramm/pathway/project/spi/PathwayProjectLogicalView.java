package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayProject;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author kotte
 */
public class PathwayProjectLogicalView implements LogicalViewProvider{
    
    private final IPathwayProject project;

    public  PathwayProjectLogicalView(IPathwayProject project) {
        this.project = project;
    }
    
    @Override
    public Node createLogicalView() {
        Node node = new AbstractNode(Children.LEAF);
        return node;
    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
}
