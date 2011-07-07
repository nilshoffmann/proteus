package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Default implementation for IPlate96.
 *
 * @author kotte
 */
public class Plate96 implements IPlate96 {

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
    String name;
    String description;
    IWell96[] wells;
    IProject parent;

    public Plate96() {
        initiateWells();
    }

    private void initiateWells() {
        this.wells = new IWell96[96];
        for (char row = 'A'; row <= 'H'; row++) {
            for (int column = 1; column <= 12; column++) {
                wells[posToIndex(row, column)] = new Well96(row, column, this);
            }
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IProject getParent() {
        return parent;
    }

    @Override
    public IWell96[] getWells() {
        return wells;
    }

    @Override
    public IWell96 getWell(char row, int column) {
        return wells[posToIndex(row, column)];
    }

    @Override
    public int getXdimension() {
        return 12;
    }

    @Override
    public int getYdimension() {
        return 8;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setName(String name) {
        this.name = name;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setParent(IProject parent) {
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setWells(IWell96[] wells) {
        this.wells = wells;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public void setWell(IWell96 well, char row, int column) {
        this.wells[posToIndex(row, column)] = well;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null, this);
    }

    @Override
    public String toString() {
        String str = "96 well plate '" + name + "': " + description;
        for (int i = 0; i < wells.length; i++) {
            str += "\n    > " + wells[i].toString();
        }
        return str;
    }

    private static int posToIndex(char row, int column) {
        assert (column >= 1 && column <= 12);
        //setze x auf 0 fuer A oder a, 1 fuer B oder b, etc.
        int x = row - 65;
        if (x >= 32) {
            x -= 32;
        }
        assert (x >= 0 && x <= 7);

        return x + (column - 1) * 8;
    }
}
