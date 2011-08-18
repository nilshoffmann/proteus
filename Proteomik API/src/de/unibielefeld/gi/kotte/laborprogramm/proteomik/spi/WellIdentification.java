/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 *
 * @author hoffmann
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
    private List<IIdentification> identifications;
    private boolean uncertain;

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
        IWell384 oldWell = this.well;
        this.well = well;
        pcs.firePropertyChange(PROPERTY_WELL, oldWell, well);
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
        List<IIdentification> oldIdentifications = this.identifications;
        this.identifications = identifications;
        pcs.firePropertyChange(PROPERTY_IDENTIFICATIONS, oldIdentifications, identifications);
    }

    @Override
    public void addIdentification(IIdentification identification) {
        activate(ActivationPurpose.WRITE);
        this.identifications.add(identification);
        pcs.firePropertyChange(PROPERTY_IDENTIFICATIONS, null, identifications);
    }

    /**
     * Get the value of uncertain
     *
     * @return the value of uncertain
     */
    @Override
    public boolean isUncertain() {
        activate(ActivationPurpose.READ);
        return uncertain;
    }

    /**
     * Set the value of uncertain
     *
     * @param uncertain new value of uncertain
     */
    @Override
    public void setUncertain(boolean uncertain) {
        activate(ActivationPurpose.WRITE);
        boolean oldUncertain = this.uncertain;
        this.uncertain = uncertain;
        pcs.firePropertyChange(PROPERTY_UNCERTAIN, oldUncertain, uncertain);
    }
}
