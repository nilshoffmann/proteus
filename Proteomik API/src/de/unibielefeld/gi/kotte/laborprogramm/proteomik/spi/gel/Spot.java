package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Default implementation for ISpot.
 *
 * @author kotte
 */
public class Spot implements ISpot {

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
    SpotStatus status;
    IGel gel;
    ISpotGroup group;
    double posX, posY;
    String label;
    int number;
    IWell96 well;

    Spot() {
        status = SpotStatus.UNPICKED;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public IGel getGel() {
        return gel;
    }

    @Override
    public double getPosX() {
        return posX;
    }

    @Override
    public double getPosY() {
        return posY;
    }

    @Override
    public SpotStatus getStatus() {
        return status;
    }

    @Override
    public IWell96 getWell() {
        return well;
    }

    @Override
    public void setWell(IWell96 well) {
        this.well = well;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setGel(IGel parent) {
        this.gel = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setPosX(double posX) {
        this.posX = posX;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setPosY(double posY) {
        this.posY = posY;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setStatus(SpotStatus status) {
        this.status = status;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public ISpotGroup getGroup() {
        return this.group;
    }

    @Override
    public void setGroup(ISpotGroup group) {
        this.group = group;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String toString() {
        return "spot #" + number + " labeled '" + label + "' at position " + posX + "/" + posY + " is " + status;
    }
}
