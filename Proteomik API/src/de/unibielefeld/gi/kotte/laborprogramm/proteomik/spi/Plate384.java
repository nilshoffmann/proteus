package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Default implementation for IPlate384.
 *
 * @author kotte
 */
public class Plate384 implements IPlate384, Activatable {

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
    private String name;
    private String description;
    private IWell384[] wells;
    private IProject parent;

    @Override
    public String getDescription() {
        activate(ActivationPurpose.READ);
        return description;
    }

    @Override
    public String getName() {
        activate(ActivationPurpose.READ);
        return name;
    }

    @Override
    public IProject getParent() {
        activate(ActivationPurpose.READ);
        return parent;
    }

    @Override
    public IWell384[] getWells() {
        activate(ActivationPurpose.READ);
        return wells;
    }

    @Override
    public IWell384 getWell(char row, int column) {
        activate(ActivationPurpose.READ);
        return getWells()[posToIndex(row, column)];
    }

    @Override
    public int getXdimension() {
        return 24;
    }

    @Override
    public int getYdimension() {
        return 16;
    }

    @Override
    public void setDescription(String description) {
        activate(ActivationPurpose.WRITE);
        this.description = description;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public void setName(String name) {
        activate(ActivationPurpose.WRITE);
        this.name = name;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public void setParent(IProject parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public void setWells(IWell384[] wells) {
        activate(ActivationPurpose.WRITE);
        this.wells = wells;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public void setWell(IWell384 well, char row, int column) {
        activate(ActivationPurpose.WRITE);
        this.wells[posToIndex(row, column)] = well;
        getPropertyChangeSupport().firePropertyChange(getClass().getName(), null,
                this);
    }

    @Override
    public String toString() {
        String str = "384 well plate '" + getName() + "': " + getDescription();
        for (int i = 0; i < getWells().length; i++) {
            str += "\n    > " + getWells()[i].toString();
        }
        return str;
    }

    public static int posToIndex(char row, int column) {
        assert (column >= 1 && column <= 24);
        //setze x auf 0 fuer A oder a, 1 fuer B oder b, etc.
        int x = row - 65;
        if (x >= 32) {
            x -= 32;
        }
        assert (x >= 0 && x <= 15);

        return x + (column - 1) * 16;
    }
}
