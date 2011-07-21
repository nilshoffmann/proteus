package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
class SpotGroupFolderChildNodeFactory extends ChildFactory<ISpotGroup> implements PropertyChangeListener {

    private Lookup lkp;
    private boolean sortSpotGroupsNumerically = false;

    public SpotGroupFolderChildNodeFactory() {
        
    }

    public SpotGroupFolderChildNodeFactory(Lookup lkp) {
        this.lkp = lkp;
    }

    @Override
    protected boolean createKeys(List<ISpotGroup> toPopulate) {
        List<ISpotGroup> l = new ArrayList<ISpotGroup>(lkp.lookupAll(ISpotGroup.class));
        if (sortSpotGroupsNumerically) {
            Collections.sort(l, new Comparator<ISpotGroup>() {

                @Override
                public int compare(ISpotGroup t, ISpotGroup t1) {
                    return t.getNumber() - t1.getNumber();
                }
            });
        }
        for (ISpotGroup isg : l) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(isg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(ISpotGroup key) {
        key.addPropertyChangeListener(this);
        return new SpotGroupNode(key, lkp);
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
        propertyChange(new PropertyChangeEvent(this,"sortSpotGroupsNumerically",oldValue,this.sortSpotGroupsNumerically));
    }
}
