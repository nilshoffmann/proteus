package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * Default implementation for IProject.
 *
 * @author kotte
 */
public class Project implements IProject, Activatable {

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
    String owner = "";
    String name = "";
    String description = "";
    List<ILogicalGelGroup> gelgroups = new ActivatableArrayList<ILogicalGelGroup>();
    List<IPlate96> plates96 = new ActivatableArrayList<IPlate96>();
    List<IPlate384> plates384 = new ActivatableArrayList<IPlate384>();
    List<ISpotGroup> spotgroups = new ActivatableArrayList<ISpotGroup>();

    @Override
    public List<ILogicalGelGroup> getGelGroups() {
        activate(ActivationPurpose.READ);
        return gelgroups;
    }

    @Override
    public void setGelGroups(List<ILogicalGelGroup> groups) {
        activate(ActivationPurpose.WRITE);
        this.gelgroups = groups;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public void addGelGroup(ILogicalGelGroup group) {
        activate(ActivationPurpose.WRITE);
        this.gelgroups.add(group);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public List<ISpotGroup> getSpotGroups() {
        activate(ActivationPurpose.READ);
        return spotgroups;
    }

    @Override
    public void setSpotGroups(List<ISpotGroup> groups) {
        activate(ActivationPurpose.WRITE);
        this.spotgroups = groups;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public void addSpotGroup(ISpotGroup group) {
        activate(ActivationPurpose.WRITE);
        this.spotgroups.add(group);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
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
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public String getOwner() {
        activate(ActivationPurpose.READ);
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        activate(ActivationPurpose.WRITE);
        this.owner = owner;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
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
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public List<IPlate96> get96Plates() {
        activate(ActivationPurpose.READ);
        return plates96;
    }

    @Override
    public void set96Plates(List<IPlate96> plates) {
        activate(ActivationPurpose.WRITE);
        this.plates96 = plates;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public List<IPlate384> get384Plates() {
        activate(ActivationPurpose.READ);
        return plates384;
    }

    @Override
    public void set384Plates(List<IPlate384> plates) {
        activate(ActivationPurpose.WRITE);
        this.plates384 = plates;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public void add96Plate(IPlate96 plate) {
        activate(ActivationPurpose.WRITE);
        this.plates96.add(plate);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public void add384Plate(IPlate384 plate) {
        activate(ActivationPurpose.WRITE);
        this.plates384.add(plate);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public String toString() {
        String str = "project '" + getName() + "' from " + getOwner() + ": " + getDescription();
//        if (!gelgroups.isEmpty()) {
//            ILogicalGelGroup group = null;
//            for (Iterator<ILogicalGelGroup> it = gelgroups.iterator(); it.hasNext();) {
//                group = it.next();
//                str += "\n  > " + group.toString();
//            }
//        } else {
//            str += "\n  no logical gel groups";
//        }
//        if (!plates384.isEmpty()) {
//            IPlate384 plate = null;
//            for (Iterator<IPlate384> it = plates384.iterator(); it.hasNext();) {
//                plate = it.next();
//                str += "\n  > " + plate.toString();
//            }
//        } else {
//            str += "\n  no 384 well plates";
//        }
//        if (!plates96.isEmpty()) {
//            IPlate96 plate = null;
//            for (Iterator<IPlate96> it = plates96.iterator(); it.hasNext();) {
//                plate = it.next();
//                str += "\n  > " + plate.toString();
//            }
//        } else {
//            str += "\n  no 96 well plates";
//        }
//        if (!spotgroups.isEmpty()) {
//            ISpotGroup group = null;
//            for (Iterator<ISpotGroup> it = spotgroups.iterator(); it.hasNext();) {
//                group = it.next();
//                str += "\n  > " + group.toString();
//            }
//        } else {
//            str += "\n  no spot groups";
//        }
        return str;
    }
}
