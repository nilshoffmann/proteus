package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
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
 * Node representing a gel spot as the child of its group.
 *
 * @author kotte
 */
public class SpotNodeAsGroupChild extends BeanNode<ISpot> implements PropertyChangeListener {

    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/SpotIcon.png";

    public SpotNodeAsGroupChild(ISpot spot, Children children, Lookup lkp) throws IntrospectionException {
        super(spot, Children.LEAF, new ProxyLookup(lkp, Lookups.fixed(spot)));
        spot.addPropertyChangeListener(WeakListeners.propertyChange(this, spot));
        spot.getGel().addPropertyChangeListener(WeakListeners.propertyChange(this, spot.getGel()));
    }

    public SpotNodeAsGroupChild(ISpot spot, Lookup lkp) throws IntrospectionException {
        super(spot, Children.LEAF, new ProxyLookup(lkp, Lookups.fixed(spot)));
        spot.addPropertyChangeListener(WeakListeners.propertyChange(this, spot));
        spot.getGel().addPropertyChangeListener(WeakListeners.propertyChange(this, spot.getGel()));
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
        return getLookup().lookup(ISpot.class).getGel().getName();
    }

    @Override
    public String getShortDescription() {
//        return getLookup().lookup(ISpot.class).getLabel();
        StringBuilder spotGroupLabel = new StringBuilder();
        boolean filled = false;
        ISpot spot = getLookup().lookup(ISpot.class);
        if (spot.getStatus() == SpotStatus.PICKED) {
            IWell96 well96 = spot.getWell();
            if (well96.getStatus() == Well96Status.PROCESSED) {
                for (IWell384 well384 : well96.get384Wells()) {
                    for (IIdentificationMethod method : well384.getIdentification().getMethods()) {
                        for (IIdentification ident : method.getIdentifications()) {
                            if (filled) {
                                spotGroupLabel.append(", ");
                            }
//                            spotGroupLabel.append(ident.getAbbreviation());
                            spotGroupLabel.append(ident.getName());
                            filled = true;
                        }
                    }
                }
            }
        }
        if (!filled) {
            if (!spot.getLabel().isEmpty()) {
                spotGroupLabel.append(spot.getLabel());
                spotGroupLabel.append(" ");
            }
            if (spot.getStatus() == SpotStatus.PICKED) {
                IWell96 well96 = spot.getWell();
                spotGroupLabel.append(well96.getParent().getName());
                spotGroupLabel.append(":");
                spotGroupLabel.append(well96.getWellPosition());
                spotGroupLabel.append(" is ");
                spotGroupLabel.append(well96.getStatus());
            } else {
                spotGroupLabel.append("unpicked Spot");
            }
        }
        return spotGroupLabel.toString();
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
        } else if (evt.getPropertyName().equals("name")) {
            this.fireDisplayNameChange(null, getDisplayName());
        } else {
            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        }
    }
}
