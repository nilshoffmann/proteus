package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Default implementation for ITechRepGelGroup.
 *
 * @author kotte
 */
public class TechRepGelGroup implements ITechRepGelGroup {

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
    IBioRepGelGroup parent;
    String name;
    String description;
    List<IGel> gels;

    public TechRepGelGroup() {
        this.name = "";
        this.description = "";
        this.gels = new ArrayList<IGel>();
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
    public List<IGel> getGels() {
        return gels;
    }

    @Override
    public void setGels(List<IGel> gels) {
        this.gels = gels;
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
    public IBioRepGelGroup getParent() {
        return parent;
    }

    @Override
    public void setParent(IBioRepGelGroup parent) {
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void addGel(IGel gel) {
        this.gels.add(gel);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String toString() {
        String str = "tech rep gel group '" + name + "': " + description;
        if (!gels.isEmpty()) {
            IGel gel = null;
            for (Iterator<IGel> it = gels.iterator(); it.hasNext();) {
                gel = it.next();
                str += "\n        > " + gel.toString();
            }
        } else {
            str += "\n        no gels";
        }
        return str;
    }
}
