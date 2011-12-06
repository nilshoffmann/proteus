package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
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
    private List<IIdentificationMethod> methods = new ActivatableArrayList<IIdentificationMethod>();

    /**
     * Get the value of well
     *
     * @return the value of well
     */
    @Override
    public IWell384 getParent() {
        activate(ActivationPurpose.READ);
        return well;
    }

    /**
     * Set the value of well
     *
     * @param well new value of well
     */
    @Override
    public void setParent(IWell384 well) {
        activate(ActivationPurpose.WRITE);
        this.well = well;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PARENT, null, well);
    }

    /**
     * Get the value of identifications
     *
     * @return the value of identifications
     */
    @Override
    public List<IIdentificationMethod> getMethods() {
        activate(ActivationPurpose.READ);
        return methods;
    }

    @Override
    public IIdentificationMethod getMethodByName(String name) {
        activate(ActivationPurpose.READ);
        IIdentificationMethod method = null;
        for (Iterator<IIdentificationMethod> it = methods.iterator(); it.hasNext();) {
            method = it.next();
            if (method.getName().equals(name)) {
                return method;
            }
        }
        //create new method
        method = new IdentificationMethod();
        method.setName(name);
        method.setParent(this);
        return method;
    }

    /**
     * Set the value of identifications
     *
     * @param identifications new value of identifications
     */
    @Override
    public void setMethods(List<IIdentificationMethod> methods) {
        activate(ActivationPurpose.WRITE);
        this.methods = methods;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_METHODS, null, methods);
    }

    @Override
    public void addMethod(IIdentificationMethod method) {
        activate(ActivationPurpose.WRITE);
        this.methods.add(method);
        getPropertyChangeSupport().firePropertyChange(PROPERTY_METHODS, null, methods);
    }

    @Override
    public String toString() {
        String str;
        if(methods.isEmpty()) {
            str = "empty well identification (unidentified well)";
        }
        str = "well identification with methods:";
        for (IIdentificationMethod method : methods) {
            str += "\n        > " + method.toString();
        }
        return str;
    }
}
