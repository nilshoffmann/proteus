/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author hoffmann
 */
public class Identification implements IIdentification, Activatable {

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
    private String method;
    private String name;
    private String id;
    private String description;

    /**
     * Get the value of method
     *
     * @return the value of method
     */
    @Override
    public String getMethod() {
        activate(ActivationPurpose.READ);
        return method;
    }

    /**
     * Set the value of method
     *
     * @param method new value of method
     */
    @Override
    public void setMethod(String method) {
        activate(ActivationPurpose.WRITE);
        String oldMethod = this.method;
        this.method = method;
        pcs.firePropertyChange(PROPERTY_METHOD, oldMethod, method);
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    @Override
    public String getName() {
        activate(ActivationPurpose.READ);
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    @Override
    public void setName(String name) {
        activate(ActivationPurpose.WRITE);
        String oldName = this.name;
        this.name = name;
        pcs.firePropertyChange(PROPERTY_NAME, oldName, name);
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    @Override
    public String getId() {
        activate(ActivationPurpose.READ);
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    @Override
    public void setId(String id) {
        activate(ActivationPurpose.WRITE);
        String oldId = this.id;
        this.id = id;
        pcs.firePropertyChange(PROPERTY_ID, oldId, id);
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    @Override
    public String getDescription() {
        activate(ActivationPurpose.READ);
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    @Override
    public void setDescription(String description) {
        activate(ActivationPurpose.WRITE);
        String oldDescription = this.description;
        this.description = description;
        pcs.firePropertyChange(PROPERTY_DESCRIPTION, oldDescription, description);
    }
}
