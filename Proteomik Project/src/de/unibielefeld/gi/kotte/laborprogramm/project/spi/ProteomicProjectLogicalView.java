package de.unibielefeld.gi.kotte.laborprogramm.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes.ProjectNode;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.nodes.*;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

class ProteomicProjectLogicalView implements LogicalViewProvider {

    private final IProteomicProject project;

    public ProteomicProjectLogicalView(IProteomicProject project) {
        this.project = project;
    }

    @Override
    public org.openide.nodes.Node createLogicalView() {
        Lookup lookup = null;
        if(project.getLookup().lookup(IProteomicProject.class)==null) {
            lookup = new ProxyLookup(project.getLookup(),Lookups.singleton(project));
        }else{
            lookup = project.getLookup();
        }
        ProjectNode node = new ProjectNode(project,lookup);
        return node;
    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
}
