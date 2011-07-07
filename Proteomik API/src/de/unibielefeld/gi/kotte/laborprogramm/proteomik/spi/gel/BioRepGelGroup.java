package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Default implementation for IBioRepGelGroup.
 *
 * @author kotte
 */
public class BioRepGelGroup implements IBioRepGelGroup {

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
    ILogicalGelGroup parent;
    String name;
    String description;
    List<ITechRepGelGroup> groups;

    public BioRepGelGroup() {
        this.name = "";
        this.description = "";
        this.groups = new ArrayList<ITechRepGelGroup>();
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
    public List<ITechRepGelGroup> getGelGroups() {
        return groups;
    }

    @Override
    public void setGelGroups(List<ITechRepGelGroup> groups) {
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
    public ILogicalGelGroup getParent() {
        return parent;
    }

    @Override
    public void setParent(ILogicalGelGroup parent) {
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void addGelGroup(ITechRepGelGroup group) {
        this.groups.add(group);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String toString() {
        String str = "bio rep gel group '" + name + "': " + description;
        if (!groups.isEmpty()) {
            ITechRepGelGroup group = null;
            for (Iterator<ITechRepGelGroup> it = groups.iterator(); it.hasNext();) {
                group = it.next();
                str += "\n      > " + group.toString();
            }
        } else {
            str += "\n      no tech rep gel groups";
        }
        return str;
    }
}
