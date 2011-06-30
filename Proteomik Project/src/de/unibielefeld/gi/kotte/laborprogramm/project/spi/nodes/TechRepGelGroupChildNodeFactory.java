package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author kotte
 */
class TechRepGelGroupChildNodeFactory extends ChildFactory<Object> {

    private ITechRepGelGroup itrgg;

    public TechRepGelGroupChildNodeFactory(ITechRepGelGroup itrgg) {
        this.itrgg = itrgg;
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        List<IGel> gels = itrgg.getGels();
        for (IGel gel : gels) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(gel);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(Object key) {
        assert (key.getClass() == IGel.class);
        return new GelNode((IGel) key);
    }
}
