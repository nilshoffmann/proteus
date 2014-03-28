package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 * Factory for creating BioRepGelGroupNode nodes as children of a LogicalGelGroupNode.
 *
 * @author Konstantin Otte
 */
class LogicalGelGroupChildNodeFactory extends ChildFactory<IBioRepGelGroup> {

    private Lookup lkp;

    public LogicalGelGroupChildNodeFactory(Lookup lkp) {
        this.lkp = lkp;
    }

    @Override
    protected boolean createKeys(List<IBioRepGelGroup> toPopulate) {
        List<IBioRepGelGroup> ibrggs = lkp.lookup(ILogicalGelGroup.class).getGelGroups();
        for (IBioRepGelGroup ibrgg : ibrggs) {
            if (Thread.interrupted()) {
                return false;
            } else {
                toPopulate.add(ibrgg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(IBioRepGelGroup key) {
        //Logger.getLogger(LogicalGelGroupChildNodeFactory.class.getName()).info("Creating node for key " + key.getClass().getName());
        if (key instanceof IBioRepGelGroup) {
            return new BioRepGelGroupNode(key,lkp);
        }
        return Node.EMPTY;
    }
}
