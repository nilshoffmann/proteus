package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author kotte
 */
class SpotGroupFolderChildNodeFactory extends ChildFactory<ISpotGroup> implements PropertyChangeListener {

    private Collection<ISpotGroup> isgs;

    public SpotGroupFolderChildNodeFactory(Collection<ISpotGroup> isgs) {
        this.isgs = isgs;
    }

    @Override
    protected boolean createKeys(List<ISpotGroup> toPopulate) {
        for (ISpotGroup isg : isgs) {
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
        return new SpotGroupNode(key);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        refresh(true);
    }
}
