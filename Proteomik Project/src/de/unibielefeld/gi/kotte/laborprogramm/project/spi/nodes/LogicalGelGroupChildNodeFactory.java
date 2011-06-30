package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author kotte
 */
class LogicalGelGroupChildNodeFactory extends ChildFactory<Object> {

    private ILogicalGelGroup illg;

    public LogicalGelGroupChildNodeFactory(ILogicalGelGroup illg) {
        this.illg = illg;
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        List<IBioRepGelGroup> ibrggs = illg.getGelGroups();
        for (IBioRepGelGroup ibrgg : ibrggs) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(ibrgg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(Object key) {
        assert (key.getClass() == IBioRepGelGroup.class);
        return new BioRepGelGroupNode((IBioRepGelGroup) key);
    }
}
