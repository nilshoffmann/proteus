package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation for IGel.
 *
 * @author kotte
 */
public class Gel implements IGel {

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
    ITechRepGelGroup parent;
    String name;
    String description;
    String filename;
    List<ISpot> spots;
    boolean virtual;

    public Gel() {
        this.name = "";
        this.description = "";
        this.filename = "";
        this.spots = new ArrayList<ISpot>();
        this.virtual = false;
    }

    @Override
    public ITechRepGelGroup getParent() {
        return parent;
    }

    @Override
    public void setParent(ITechRepGelGroup parent) {
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
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
    public String getFilename() {
        return filename;
    }

    @Override
    public void setFilename(String filename) {
        this.filename = filename;
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
    public List<ISpot> getSpots() {
        return spots;
    }

    @Override
    public void addSpot(ISpot spot) {
        this.spots.add(spot);
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setSpots(List<ISpot> spots) {
        this.spots = spots;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public boolean isVirtual(){
        return this.virtual;
    }

    @Override
    public void setVirtual(boolean virtual){
        this.virtual = virtual;
    }

    @Override
    public String toString() {
        String str = "gel '" + name + "' from file '" + filename + "': " + description;
//        if (!spots.isEmpty()) {
//            ISpot spot = null;
//            for (Iterator<ISpot> it = spots.iterator(); it.hasNext();) {
//                spot = it.next();
//                str += "\n          > " + spot.toString();
//            }
//        } else {
//            str += "\n          no spots";
//        }
        return str;
    }
}
