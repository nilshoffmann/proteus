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
import org.openide.util.WeakListeners;

/**
 *
 * @author kotte
 */
class SpotGroupFolderChildNodeFactory extends ChildFactory<ISpotGroup> implements PropertyChangeListener {

    private Lookup lkp;
    private boolean sortSpotGroupsNumerically = false;
    private boolean sortSpotGroupsByLabel = false;
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
        }else if(sortSpotGroupsByLabel){
            Collections.sort(l, new Comparator<ISpotGroup>() {

                @Override
                public int compare(ISpotGroup t, ISpotGroup t1) {
                    if(t.getLabel()==null || t.getLabel().isEmpty()) {
                        return 1;
                    }
                    if(t1.getLabel()==null || t1.getLabel().isEmpty()) {
                        return -1;
                    }
                    return t.getLabel().compareTo(t1.getLabel());
                }
            });
        }
        for (ISpotGroup isg : l) {
            if (Thread.interrupted()) {
                return false;
            } else {
                toPopulate.add(isg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(ISpotGroup key) {
        key.addPropertyChangeListener(WeakListeners.propertyChange(this, key));
        return new SpotGroupNode(key, lkp);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        refresh(true);
    }

    public boolean isSortSpotGroupsNumerically() {
        return sortSpotGroupsNumerically;
    }
    
    public boolean isSortSpotGroupsByLabel() {
        return sortSpotGroupsNumerically;
    }

    public void setSortSpotGroupsNumerically(boolean sortSpotGroupsNumerically) {
        boolean oldValue = this.sortSpotGroupsNumerically;
        this.sortSpotGroupsNumerically = sortSpotGroupsNumerically;
        propertyChange(new PropertyChangeEvent(this,"sortSpotGroupsNumerically",oldValue,this.sortSpotGroupsNumerically));
    }
    
    public void setSortSpotGroupsByLabel(boolean sortSpotGroupsByLabel) {
        boolean oldValue = this.sortSpotGroupsByLabel;
        this.sortSpotGroupsByLabel = sortSpotGroupsByLabel;
        propertyChange(new PropertyChangeEvent(this,"sortSpotGroupsByLabel",oldValue,this.sortSpotGroupsByLabel));
    }
}
