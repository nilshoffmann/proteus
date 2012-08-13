package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
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
 * Factory for creating SpotNodeAsGroupChild nodes as children of a SpotGroupNode.
 *
 * @author kotte
 */
class SpotGroupChildNodeFactory extends ChildFactory<ISpot> implements PropertyChangeListener {

    private Lookup lkp;
    private boolean sortSpotsByGel = true;

    public SpotGroupChildNodeFactory() {
    }

    public SpotGroupChildNodeFactory(Lookup lkp) {
        this.lkp = lkp;
    }

    @Override
    protected boolean createKeys(List<ISpot> toPopulate) {
        ISpotGroup group = lkp.lookup(ISpotGroup.class);
        List<ISpot> l = group.getSpots();
        if (sortSpotsByGel) {
            Collections.sort(l, new Comparator<ISpot>() {

                @Override
                public int compare(ISpot spot1, ISpot spot2) {
                    return spot1.getGel().getName().compareTo(spot2.getGel().getName());
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
            return new SpotNodeAsGroupChild(key,lkp);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return Node.EMPTY;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        refresh(true);
    }

    public boolean isSortSpotsByGel() {
        return sortSpotsByGel;
    }

    public void setSortSpotsByGel(boolean sortSpotGroupsNumerically) {
        boolean oldValue = this.sortSpotsByGel;
        this.sortSpotsByGel = sortSpotGroupsNumerically;
        propertyChange(new PropertyChangeEvent(this, "sortSpotsByGel", oldValue, this.sortSpotsByGel));
    }
}
