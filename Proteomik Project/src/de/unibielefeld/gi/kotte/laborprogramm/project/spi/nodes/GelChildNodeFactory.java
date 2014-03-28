package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * Factory for creating SpotNodeAsGelChild nodes as children of a GelNode.
 *
 * @author Konstantin Otte
 */
class GelChildNodeFactory extends ChildFactory<ISpot> implements PropertyChangeListener {

    private Lookup lkp;
    private boolean sortSpotGroupsNumerically = true;

    public GelChildNodeFactory() {
    }

    public GelChildNodeFactory(Lookup lkp) {
        this.lkp = lkp;
    }

    @Override
    protected boolean createKeys(List<ISpot> toPopulate) {
        IGel gel = lkp.lookup(IGel.class);
        List<ISpot> l = gel.getSpots();
        if (sortSpotGroupsNumerically) {
            Collections.sort(l, new Comparator<ISpot>() {

                @Override
                public int compare(ISpot t, ISpot t1) {
                    return t.getNumber() - t1.getNumber();
                }
            });
        }
        for (ISpot isg : l) {
            if (Thread.interrupted()) {
                return false;
            } else {
                toPopulate.add(isg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(ISpot key) {
        key.addPropertyChangeListener(this);
        try {
            return new SpotNodeAsGelChild(key,lkp);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return Node.EMPTY;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        refresh(true);
    }

    public boolean isSortSpotGroupsNumerically() {
        return sortSpotGroupsNumerically;
    }

    public void setSortSpotGroupsNumerically(boolean sortSpotGroupsNumerically) {
        boolean oldValue = this.sortSpotGroupsNumerically;
        this.sortSpotGroupsNumerically = sortSpotGroupsNumerically;
        propertyChange(new PropertyChangeEvent(this, "sortSpotGroupsNumerically", oldValue, this.sortSpotGroupsNumerically));
    }
}
