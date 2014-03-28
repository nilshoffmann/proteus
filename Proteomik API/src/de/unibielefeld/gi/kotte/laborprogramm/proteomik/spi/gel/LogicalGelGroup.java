package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.UUID;

/**
 * Default implementation for ILogicalGelGroup.
 *
 * @author Konstantin Otte
 */
public class LogicalGelGroup implements ILogicalGelGroup, Activatable {

    /**
     * PropertyChangeSupport ala JavaBeans(tm)
     * Not persisted!
     */
    private transient PropertyChangeSupport pcs = null;

    @Override
    public synchronized void removePropertyChangeListener(
            PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    @Override
    public synchronized void addPropertyChangeListener(
            PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    private PropertyChangeSupport getPropertyChangeSupport() {
        if (this.pcs == null) {
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
    private IProject parent;
    private String name = "";
    private String description = "";
    private List<IBioRepGelGroup> groups = new ActivatableArrayList<IBioRepGelGroup>();

    @Override
    public String getDescription() {
        activate(ActivationPurpose.READ);
        return description;
    }

    @Override
    public void setDescription(String description) {
        activate(ActivationPurpose.WRITE);
        this.description = description;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_DESCRIPTION, null,
                description);
    }

    @Override
    public List<IBioRepGelGroup> getGelGroups() {
        activate(ActivationPurpose.READ);
        return groups;
    }

    @Override
    public void setGelGroups(List<IBioRepGelGroup> groups) {
        activate(ActivationPurpose.WRITE);
        this.groups = groups;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_GROUPS, null,
                groups);
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
    public IProject getParent() {
        activate(ActivationPurpose.READ);
        return parent;
    }

    @Override
    public void setParent(IProject parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PARENT, null,
                parent);
    }

    @Override
    public void addGelGroup(IBioRepGelGroup group) {
        activate(ActivationPurpose.WRITE);
        this.groups.add(group);
        getPropertyChangeSupport().firePropertyChange(PROPERTY_GROUPS, null,
                this.groups);
    }

    @Override
    public String toString() {
        return "logical gel group '" + getName() + "': " + getDescription();
    }

    @Override
    public String toFullyRecursiveString() {
        String str = "logical gel group '" + getName() + "': " + getDescription();
        if (!getGelGroups().isEmpty()) {
            IBioRepGelGroup group = null;
            for (Iterator<IBioRepGelGroup> it = getGelGroups().iterator(); it.
                    hasNext();) {
                group = it.next();
                str += "\n    > " + group.toString();
            }
        } else {
            str += "\n    no bio rep gel groups";
        }
        return str;
    }
    private UUID objectId = UUID.randomUUID();

    @Override
    public UUID getId() {
        activate(ActivationPurpose.READ);
        return objectId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogicalGelGroup other = (LogicalGelGroup) obj;
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
