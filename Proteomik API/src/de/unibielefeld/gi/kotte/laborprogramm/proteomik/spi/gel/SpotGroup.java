package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

/**
 * Default implementation for ISpotGroup.
 *
 * @author kotte
 */
public class SpotGroup implements ISpotGroup, Activatable {

    /**
     * PropertyChangeSupport ala JavaBeans(tm)
     * Not persisted!
     */
    private transient PropertyChangeSupport pcs = null;

    @Override
    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    @Override
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    private PropertyChangeSupport getPropertyChangeSupport() {
        if(this.pcs == null) {
            this.pcs = new PropertyChangeSupport(this);
        }
        return this.pcs;
    }

    private transient Activator activator;

    @Override
    public void bind(Activator activator) {
        if (this.activator == activator) {
            return;
        }
        if (activator != null && null != this.activator) {
            throw new IllegalStateException(
                    "Object can only be bound to one activator");
        }
        this.activator = activator;
    }

    @Override
    public void activate(ActivationPurpose activationPurpose) {
        if (null != activator) {
            activator.activate(activationPurpose);
        }
    }

    /**
     * Object definition
     */
    IProject parent;
    String label;
    int number;
    List<ISpot> spots = new ActivatableArrayList<ISpot>();

    @Override
    public IProject getParent() {
        activate(ActivationPurpose.READ);
        return parent;
    }

    @Override
    public void setParent(IProject parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String getLabel() {
        activate(ActivationPurpose.READ);
        return label;
    }

    @Override
    public void setLabel(String label) {
        activate(ActivationPurpose.WRITE);
        this.label = label;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public int getNumber() {
        activate(ActivationPurpose.READ);
        return number;
    }

    @Override
    public void setNumber(int number) {
        activate(ActivationPurpose.WRITE);
        this.number = number;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public List<ISpot> getSpots() {
        activate(ActivationPurpose.READ);
        return spots;
    }

    @Override
    public void setSpots(List<ISpot> spots) {
        activate(ActivationPurpose.WRITE);
        this.spots = spots;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void addSpot(ISpot spot) {
        activate(ActivationPurpose.WRITE);
        this.spots.add(spot);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String toString() {
        String str = "spot group #" + getNumber() + ": " + getLabel()==null?"N.N.":getLabel();
        if (!getSpots().isEmpty()) {
            ISpot spot = null;
            for (Iterator<ISpot> it = getSpots().iterator(); it.hasNext();) {
                spot = it.next();
                str += "\n    > " + spot.toString();
            }
        } else {
            str += "\n    no spots";
        }
        return str;
    }
}
