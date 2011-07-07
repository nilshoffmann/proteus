package de.unibielefeld.gi.kotte.laborprogramm.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes.ProjectNode;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.nodes.*;

class ProteomicProjectLogicalView implements LogicalViewProvider {

    private final ProteomicProject project;

    public ProteomicProjectLogicalView(ProteomicProject project) {
        this.project = project;
    }

    @Override
    public org.openide.nodes.Node createLogicalView() {
        return new ProjectNode(project);
    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
}
