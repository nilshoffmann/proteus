package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.List;

/**
 * Default implementation for IGel.
 *
 * @author kotte
 */
public class Gel implements IGel, Activatable {

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
    private ITechRepGelGroup parent;
    private String name = "";
    private String description = "";
    private String filename = "";
    private List<ISpot> spots = new ActivatableArrayList<ISpot>();
    private boolean virtual = false;

    @Override
    public ITechRepGelGroup getParent() {
        activate(ActivationPurpose.READ);
        return parent;
    }

    @Override
    public void setParent(ITechRepGelGroup parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PARENT, null, parent);
    }

    @Override
    public String getDescription() {
        activate(ActivationPurpose.READ);
        return description;
    }

    @Override
    public void setDescription(String description) {
        activate(ActivationPurpose.WRITE);
        this.description = description;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_DESCRIPTION, null, description);
    }

    @Override
    public String getFilename() {
        activate(ActivationPurpose.READ);
        return filename;
    }

    @Override
    public void setFilename(String filename) {
        activate(ActivationPurpose.WRITE);
        this.filename = filename;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_FILENAME, null, filename);
    }

    @Override
    public String getName() {
        activate(ActivationPurpose.READ);
        return name;
    }

    @Override
    public void setName(String name) {
        activate(ActivationPurpose.WRITE);
        this.name = name;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_NAME, null, name);
    }

    @Override
    public List<ISpot> getSpots() {
        activate(ActivationPurpose.READ);
        return spots;
    }

    @Override
    public void addSpot(ISpot spot) {
        activate(ActivationPurpose.WRITE);
        this.spots.add(spot);
        getPropertyChangeSupport().firePropertyChange(PROPERTY_SPOTS, null, this.spots);
    }

    @Override
    public void setSpots(List<ISpot> spots) {
        activate(ActivationPurpose.WRITE);
        this.spots = spots;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_SPOTS, null, spots);
    }

    @Override
    public boolean isVirtual(){
        activate(ActivationPurpose.READ);
        return this.virtual;
    }

    @Override
    public void setVirtual(boolean virtual){
        activate(ActivationPurpose.WRITE);
        this.virtual = virtual;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_VIRTUAL, null, virtual);
    }

    @Override
    public String toString() {
        return "gel '" + getName() + "' from file '" + getFilename() + "': " + getDescription();
    }
    
    @Override
    public String toFullyRecursiveString() {
        String str = "gel '" + getName() + "' from file '" + getFilename() + "': " + getDescription();
        if (!spots.isEmpty()) {
            ISpot spot = null;
            for (Iterator<ISpot> it = spots.iterator(); it.hasNext();) {
                spot = it.next();
                str += "\n          > " + spot.toString();
            }
        } else {
            str += "\n          no spots";
        }
        return str;
    }
}
