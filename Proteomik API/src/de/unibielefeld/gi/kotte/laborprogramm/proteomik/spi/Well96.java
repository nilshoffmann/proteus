package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * Default implementation for IWell96.
 *
 * @author kotte
 */
public class Well96 implements IWell96, Activatable {

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
    private IPlate96 parent;
    private Well96Status status = Well96Status.EMPTY;
    private ISpot spot;
    private char row = 'X';
    private int column = 0;
    private List<IWell384> wells384 = new ActivatableArrayList<IWell384>();

    @Override
    public IPlate96 getParent() {
        activate(ActivationPurpose.READ);
        return parent;
    }

    @Override
    public char getRow() {
        activate(ActivationPurpose.READ);
        return row;
    }

    @Override
    public int getColumn() {
        activate(ActivationPurpose.READ);
        return column;
    }

    @Override
    public String getWellPosition() {
        activate(ActivationPurpose.READ);
        return "" + getRow() + getColumn();
    }

    @Override
    public Well96Status getStatus() {
        activate(ActivationPurpose.READ);
        return status;
    }

    @Override
    public ISpot getSpot() {
        activate(ActivationPurpose.READ);
        return spot;
    }

    @Override
    public void setParent(IPlate96 parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PARENT, null, parent);
    }

    @Override
    public void setRow(char posX) {
        activate(ActivationPurpose.WRITE);
        this.row = posX;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_POSITION, null, getWellPosition());
    }

    @Override
    public void setColumn(int posY) {
        activate(ActivationPurpose.WRITE);
        this.column = posY;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_POSITION, null, getWellPosition());
    }

    @Override
    public void setStatus(Well96Status status) {
        activate(ActivationPurpose.WRITE);
        this.status = status;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_STATUS, null, status);
    }

    @Override
    public void setSpot(ISpot spot) {
        activate(ActivationPurpose.WRITE);
        this.spot = spot;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_SPOT, null, spot);
    }

    @Override
    public List<IWell384> get384Wells() {
        activate(ActivationPurpose.READ);
        return wells384;
    }

    @Override
    public void set384Wells(List<IWell384> wells) {
        activate(ActivationPurpose.WRITE);
        this.wells384 = wells;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_WELLS384, null, wells);
    }

    @Override
    public void add384Well(IWell384 well) {
        activate(ActivationPurpose.WRITE);
        this.wells384.add(well);
        getPropertyChangeSupport().firePropertyChange(PROPERTY_WELLS384, null, this.wells384);
    }

    @Override
    public String toString() {
        return "well " + getRow() + getColumn() + " is " + getStatus();
    }
}
