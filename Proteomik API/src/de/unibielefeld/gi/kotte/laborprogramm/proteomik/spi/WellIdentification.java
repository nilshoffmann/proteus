package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * Default implementation of IWellIdentification
 *
 * @author kotte, hoffmann
 */
public class WellIdentification implements IWellIdentification, Activatable {

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
    private IWell384 well;
    private List<IIdentification> identifications = new ActivatableArrayList<IIdentification>();

    /**
     * Get the value of well
     *
     * @return the value of well
     */
    @Override
    public IWell384 getWell() {
        activate(ActivationPurpose.READ);
        return well;
    }

    /**
     * Set the value of well
     *
     * @param well new value of well
     */
    @Override
    public void setWell(IWell384 well) {
        activate(ActivationPurpose.WRITE);
        this.well = well;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_WELL, null, well);
    }

    /**
     * Get the value of identifications
     *
     * @return the value of identifications
     */
    @Override
    public List<IIdentification> getIdentifications() {
        activate(ActivationPurpose.READ);
        return identifications;
    }

    /**
     * Set the value of identifications
     *
     * @param identifications new value of identifications
     */
    @Override
    public void setIdentifications(List<IIdentification> identifications) {
        activate(ActivationPurpose.WRITE);
        this.identifications = identifications;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_IDENTIFICATIONS, null, identifications);
    }

    @Override
    public void addIdentification(IIdentification identification) {
        activate(ActivationPurpose.WRITE);
        this.identifications.add(identification);
        getPropertyChangeSupport().firePropertyChange(PROPERTY_IDENTIFICATIONS, null, identifications);
    }

    @Override
    public String toString() {
        String str = "well identifications:";
        for (IIdentification identification : identifications) {
            str += "\n        > " + identification.toString();
        }
        return str;
    }
}
