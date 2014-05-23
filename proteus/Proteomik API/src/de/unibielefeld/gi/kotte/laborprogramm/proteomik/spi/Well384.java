package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.WellIdentification;
import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.UUID;

/**
 * Default implementation for IWell384.
 *
 * @author Konstantin Otte
 */
public class Well384 implements IWell384, Activatable {

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
    private IPlate384 parent;
    private Well384Status status = Well384Status.EMPTY;
    private IWell96 well96;
    private IWellIdentification identification = null;
    private char row = 'X';
    private int column = 0;

    public Well384(char posX, int posY, IPlate384 parent) {
        this.parent = parent;
        this.status = Well384Status.EMPTY;
        this.well96 = null;
        this.identification = new WellIdentification();
        this.identification.setParent(this);
        this.row = posX;
        this.column = posY;
    }

    @Override
    public IPlate384 getParent() {
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
    public Well384Status getStatus() {
        activate(ActivationPurpose.READ);
        return status;
    }

    @Override
    public IWell96 getWell96() {
        activate(ActivationPurpose.READ);
        return well96;
    }

    @Override
    public IWellIdentification getIdentification() {
        activate(ActivationPurpose.READ);
        if (identification == null) {
            setIdentification(new WellIdentification());
        }
        if (identification.getParent() == null) {
            identification.setParent(this);
        }
        return identification;
    }

    @Override
    public void setIdentification(IWellIdentification identification) {
        activate(ActivationPurpose.WRITE);
        this.identification = identification;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_IDENTIFICATION,
                null, identification);
    }

    @Override
    public void setParent(IPlate384 parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PARENT, null,
                parent);
    }

    @Override
    public void setRow(char posX) {
        activate(ActivationPurpose.WRITE);
        this.row = posX;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_POSITION, null,
                getWellPosition());
    }

    @Override
    public void setColumn(int posY) {
        activate(ActivationPurpose.WRITE);
        this.column = posY;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_POSITION, null,
                getWellPosition());
    }

    @Override
    public void setStatus(Well384Status status) {
        activate(ActivationPurpose.WRITE);
        this.status = status;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_STATUS, null,
                status);
    }

    @Override
    public void setWell96(IWell96 well96) {
        activate(ActivationPurpose.WRITE);
        this.well96 = well96;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_WELL96, null,
                well96);
    }

    @Override
    public String toString() {
        return "well " + getRow() + getColumn() + " is " + getStatus();
    }

    @Override
    public String toFullyRecursiveString() {
        String str = "well " + getRow() + getColumn() + " is " + getStatus();
        str += "\n      > " + identification;
        return str;
    }
    private UUID objectId = UUID.randomUUID();

    @Override
    public UUID getId() {
        activate(ActivationPurpose.READ);
        return objectId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Well384 other = (Well384) obj;
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
