package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

/**
 * Default implementation for ITechRepGelGroup.
 *
 * @author kotte
 */
public class TechRepGelGroup implements ITechRepGelGroup, Activatable {

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
    IBioRepGelGroup parent;
    String name;
    String description;
    List<IGel> gels = new ActivatableArrayList<IGel>();

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
    public List<IGel> getGels() {
        activate(ActivationPurpose.READ);
        return gels;
    }

    @Override
    public void setGels(List<IGel> gels) {
        activate(ActivationPurpose.WRITE);
        this.gels = gels;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_GELS, null, gels);
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
    public IBioRepGelGroup getParent() {
        activate(ActivationPurpose.READ);
        return parent;
    }

    @Override
    public void setParent(IBioRepGelGroup parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PARENT, null, parent);
    }

    @Override
    public void addGel(IGel gel) {
        activate(ActivationPurpose.WRITE);
        this.gels.add(gel);
        getPropertyChangeSupport().firePropertyChange(PROPERTY_GELS, null, this.gels);
    }

    @Override
    public String toString() {
        return "tech rep gel group '" + getName() + "': " + getDescription();
    }

    @Override
    public String toFullyRecursiveString() {
        String str = "tech rep gel group '" + getName() + "': " + getDescription();
        if (!getGels().isEmpty()) {
            IGel gel = null;
            for (Iterator<IGel> it = getGels().iterator(); it.hasNext();) {
                gel = it.next();
                str += "\n        > " + gel.toString();
            }
        } else {
            str += "\n        no gels";
        }
        return str;
    }
}
