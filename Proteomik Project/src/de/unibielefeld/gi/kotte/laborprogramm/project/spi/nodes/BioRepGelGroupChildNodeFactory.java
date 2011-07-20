package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
class BioRepGelGroupChildNodeFactory extends ChildFactory<ITechRepGelGroup> {

    private Lookup lkp;

    public BioRepGelGroupChildNodeFactory(Lookup lkp) {
        this.lkp = lkp;
    }

    @Override
    protected boolean createKeys(List<ITechRepGelGroup> toPopulate) {
        List<ITechRepGelGroup> itrggs = lkp.lookup(IBioRepGelGroup.class).getGelGroups();
        for (ITechRepGelGroup itrgg : itrggs) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(itrgg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(ITechRepGelGroup key) {
        if (key != null) {
            return new TechRepGelGroupNode(key,lkp);
        }
        return Node.EMPTY;
    }
}
