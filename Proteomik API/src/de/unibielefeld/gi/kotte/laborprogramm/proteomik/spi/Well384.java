package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Default implementation for IWell384.
 *
 * @author kotte
 */
public class Well384 implements IWell384 {

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
    IPlate384 parent;
    Well384Status status;
    IWell96 well96;
    String identification;
    char row;
    int column;

    public Well384() {
        this.parent = null;
        this.status = Well384Status.EMPTY;
        this.well96 = null;
        this.identification = "";
        this.row = 'X'; //Well96 Position X0 fuer ausserhalb einer Platte
        this.column = 0;
    }

    public Well384(char posX, int posY, IPlate384 parent) {
        this.parent = parent;
        this.status = Well384Status.EMPTY;
        this.well96 = null;
        this.identification = "";
        this.row = posX;
        this.column = posY;
    }

    @Override
    public IPlate384 getParent() {
        return parent;
    }

    @Override
    public char getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String getWellPosition() {
        return "" + row + column;
    }

    @Override
    public Well384Status getStatus() {
        return status;
    }

    @Override
    public IWell96 getWell96() {
        return well96;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setParent(IPlate384 parent) {
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setRow(char posX) {
        this.row = posX;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setColumn(int posY) {
        this.column = posY;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setStatus(Well384Status status) {
        this.status = status;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setWell96(IWell96 well96) {
        this.well96 = well96;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String toString() {
        return "well " + row + column + " is " + status;
    }
}
