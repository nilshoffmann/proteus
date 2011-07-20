package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
class TechRepGelGroupChildNodeFactory extends ChildFactory<IGel> {
    
    private Lookup lkp;

    public TechRepGelGroupChildNodeFactory(Lookup lkp) {
        this.lkp = lkp;
    }

    @Override
    protected boolean createKeys(List<IGel> toPopulate) {
        List<IGel> gels = lkp.lookup(ITechRepGelGroup.class).getGels();
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
    protected Node createNodeForKey(IGel key) {
        if(key!=null) {
            return new GelNode(key,lkp);
        }
        return Node.EMPTY;
    }
}
