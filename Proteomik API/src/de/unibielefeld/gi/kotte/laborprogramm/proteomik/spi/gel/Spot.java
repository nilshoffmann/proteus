package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import java.awt.Shape;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.UUID;

/**
 * Default implementation for ISpot.
 *
 * @author kotte
 */
public class Spot implements ISpot, Activatable {

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
    SpotStatus status = SpotStatus.UNPICKED;
    IGel gel;
    ISpotGroup group;
    double posX, posY;
    String label;
    int number;
    IWell96 well;
    Shape shape;

    @Override
    public String getLabel() {
        activate(ActivationPurpose.READ);
        return label;
    }

    @Override
    public int getNumber() {
        activate(ActivationPurpose.READ);
        return number;
    }

    @Override
    public IGel getGel() {
        activate(ActivationPurpose.READ);
        return gel;
    }

    @Override
    public double getPosX() {
        activate(ActivationPurpose.READ);
        return posX;
    }

    @Override
    public double getPosY() {
        activate(ActivationPurpose.READ);
        return posY;
    }

    @Override
    public SpotStatus getStatus() {
        activate(ActivationPurpose.READ);
        return status;
    }

    @Override
    public IWell96 getWell() {
        activate(ActivationPurpose.READ);
        return well;
    }

    @Override
    public void setWell(IWell96 well) {
        activate(ActivationPurpose.WRITE);
        this.well = well;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_WELL, null, well);
    }

    @Override
    public void setLabel(String label) {
        activate(ActivationPurpose.WRITE);
        this.label = label;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_LABEL, null,
                label);
    }

    @Override
    public void setNumber(int number) {
        activate(ActivationPurpose.WRITE);
        this.number = number;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_NUMBER, null,
                number);
    }

    @Override
    public void setGel(IGel gel) {
        activate(ActivationPurpose.WRITE);
        this.gel = gel;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_GEL, null, gel);
    }

    @Override
    public void setPosX(double posX) {
        activate(ActivationPurpose.WRITE);
        this.posX = posX;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_POS_X, null, posX);
    }

    @Override
    public void setPosY(double posY) {
        activate(ActivationPurpose.WRITE);
        this.posY = posY;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_POS_Y, null, posY);
    }

    @Override
    public void setStatus(SpotStatus status) {
        activate(ActivationPurpose.WRITE);
        this.status = status;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_STATUS, null,
                status);
    }

    @Override
    public ISpotGroup getGroup() {
        activate(ActivationPurpose.READ);
        return this.group;
    }

    @Override
    public void setGroup(ISpotGroup group) {
        activate(ActivationPurpose.WRITE);
        this.group = group;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_GROUP, null,
                group);
    }

    @Override
    public String toString() {
        return "spot #" + getNumber() + " labeled '" + getLabel() + "' at position " + getPosX() + "/" + getPosY() + " is " + getStatus();
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
        final Spot other = (Spot) obj;
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public Shape getShape() {
        activate(ActivationPurpose.READ);
        return this.shape;
    }

    @Override
    public void setShape(Shape shape) {
        activate(ActivationPurpose.WRITE);
        System.out.println("setShape called on Spot with "+shape.getBounds2D());
        this.shape = shape;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_SHAPE, null,
                shape);
    }
}
