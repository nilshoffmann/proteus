package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

/**
 * Default implementation for IBioRepGelGroup.
 *
 * @author kotte
 */
public class BioRepGelGroup implements IBioRepGelGroup, Activatable {

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
    private ILogicalGelGroup parent;
    private String name = "";
    private String description = "";
    private List<ITechRepGelGroup> groups = new ActivatableArrayList<ITechRepGelGroup>();

    @Override
    public String getDescription() {
        activate(ActivationPurpose.READ);
        return description;
    }

    @Override
    public void setDescription(String description) {
        activate(ActivationPurpose.WRITE);
        this.description = description;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public List<ITechRepGelGroup> getGelGroups() {
        activate(ActivationPurpose.READ);
        return groups;
    }

    @Override
    public void setGelGroups(List<ITechRepGelGroup> groups) {
        activate(ActivationPurpose.WRITE);
        this.groups = groups;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
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
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public ILogicalGelGroup getParent() {
        activate(ActivationPurpose.READ);
        return parent;
    }

    @Override
    public void setParent(ILogicalGelGroup parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void addGelGroup(ITechRepGelGroup group) {
        activate(ActivationPurpose.WRITE);
        this.groups.add(group);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String toString() {
        String str = "bio rep gel group '" + getName() + "': " + getDescription();
        if (!getGelGroups().isEmpty()) {
            ITechRepGelGroup group = null;
            for (Iterator<ITechRepGelGroup> it = getGelGroups().iterator(); it.hasNext();) {
                group = it.next();
                str += "\n      > " + group.toString();
            }
        } else {
            str += "\n      no tech rep gel groups";
        }
        return str;
    }
}
