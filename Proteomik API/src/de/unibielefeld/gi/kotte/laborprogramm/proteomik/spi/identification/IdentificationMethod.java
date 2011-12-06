package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 *
 * @author kotte
 */
public class IdentificationMethod implements IIdentificationMethod, Activatable{

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
    IWellIdentification parent = null;
    String name;
    List<IIdentification> identifications = new ActivatableArrayList<IIdentification>();

    @Override
    public void addIdentification(IIdentification identification) {
        activate(ActivationPurpose.WRITE);
        this.identifications.add(identification);
        getPropertyChangeSupport().firePropertyChange(PROPERTY_IDENTIFICATIONS, null, this.identifications);
    }

    @Override
    public List<IIdentification> getIdentifications() {
        activate(ActivationPurpose.READ);
        return identifications;
    }

    @Override
    public IWellIdentification getParent() {
        activate(ActivationPurpose.READ);
        return parent;
    }

    @Override
    public void setIdentifications(List<IIdentification> identifications) {
        activate(ActivationPurpose.WRITE);
        this.identifications = identifications;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_IDENTIFICATIONS, null, identifications);
    }

    @Override
    public void setParent(IWellIdentification parent) {
        activate(ActivationPurpose.WRITE);
        this.parent = parent;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PARENT, null, parent);
    }

    @Override
    public String getName() {
        activate(ActivationPurpose.READ);
        return name;
    }

    @Override
    public void setName(String name) {
        activate(ActivationPurpose.WRITE);
        this.name = name;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_NAME, null, name);
    }

    @Override
    public String toString() {
        String str;
        if(identifications.isEmpty()) {
            str = "empty identification method";
        }
        str = "identification method '" + name + "' found the following identifications:";
        for (IIdentification identification : identifications) {
            str += "\n          > " + identification.toString();
        }
        return str;
    }
}
