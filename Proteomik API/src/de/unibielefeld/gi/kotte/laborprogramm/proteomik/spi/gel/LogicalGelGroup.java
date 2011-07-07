package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Default implementation for ILogicalGelGroup.
 *
 * @author kotte
 */
public class LogicalGelGroup implements ILogicalGelGroup {

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
    /**
     * Object definition
     */
    IProject parent;
    String name;
    String description;
    List<IBioRepGelGroup> groups;

    public LogicalGelGroup() {
        this.name = "";
        this.description = "";
        this.groups = new ArrayList<IBioRepGelGroup>();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public List<IBioRepGelGroup> getGelGroups() {
        return groups;
    }

    @Override
    public void setGelGroups(List<IBioRepGelGroup> groups) {
        this.groups = groups;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public IProject getParent() {
        return parent;
    }

    @Override
    public void setParent(IProject parent) {
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void addGelGroup(IBioRepGelGroup group) {
        this.groups.add(group);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String toString() {
        String str = "logical gel group '" + name + "': " + description;
        if (!groups.isEmpty()) {
            IBioRepGelGroup group = null;
            for (Iterator<IBioRepGelGroup> it = groups.iterator(); it.hasNext();) {
                group = it.next();
                str += "\n    > " + group.toString();
            }
        } else {
            str += "\n    no bio rep gel groups";
        }
        return str;
    }
}
