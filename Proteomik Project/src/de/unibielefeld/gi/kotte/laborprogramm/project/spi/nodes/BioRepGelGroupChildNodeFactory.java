package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author kotte
 */
class BioRepGelGroupChildNodeFactory extends ChildFactory<Object> {

    private IBioRepGelGroup ibrgg;

    public BioRepGelGroupChildNodeFactory(IBioRepGelGroup ibrgg) {
        this.ibrgg = ibrgg;
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        List<ITechRepGelGroup> itrggs = ibrgg.getGelGroups();
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
    protected Node createNodeForKey(Object key) {
        assert (key.getClass() == ITechRepGelGroup.class);
        return new TechRepGelGroupNode((ITechRepGelGroup)key);
    }
}
