package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.Image;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 * Node representing a gel spot as the child of its gel.
 *
 * @author kotte
 */
public class SpotNodeAsGelChild extends BeanNode<ISpot> implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/SpotIcon.png";

    public SpotNodeAsGelChild(ISpot spot, Children children, Lookup lkp) throws IntrospectionException {
        super(spot, Children.LEAF, new ProxyLookup(lkp, Lookups.fixed(spot)));
        spot.addPropertyChangeListener(WeakListeners.propertyChange(this, spot));
        spot.getGel().addPropertyChangeListener(WeakListeners.propertyChange(this, spot.getGel()));
    }

    public SpotNodeAsGelChild(ISpot spot, Lookup lkp) throws IntrospectionException {
        super(spot, Children.LEAF, new ProxyLookup(lkp, Lookups.fixed(spot)));
        spot.addPropertyChangeListener(WeakListeners.propertyChange(this, spot));
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(ICON_PATH);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        return getLookup().lookup(ISpot.class).getNumber() + "";
    }

    @Override
    public String getShortDescription() {
        return getLookup().lookup(ISpot.class).getLabel();
    }

    @Override
    public Action[] getActions(boolean arg0) {
        List<? extends Action> actions = Utilities.actionsForPath(
                "/Actions/SpotNode");
        List<Action> allActions = new LinkedList<Action>(actions);
        return allActions.toArray(new Action[allActions.size()]);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("displayName")) {
            this.fireDisplayNameChange(null, getDisplayName());
        }else if (evt.getPropertyName().equals("name")) {
            this.fireDisplayNameChange(null, getDisplayName());
        } else {
            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        }
    }
}