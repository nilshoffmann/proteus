/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 *
 * @author hoffmann
 */
public class GelSpotAnnotations implements Activatable, IPropertyChangeSource {

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

    public static final String PROP_GEL = "gel";

    private IGel gel;

    public IGel getGel() {
        activate(ActivationPurpose.READ);
        return gel;
    }

    public void setGel(IGel gel) {
        activate(ActivationPurpose.WRITE);
        IGel old = this.gel;
        this.gel = gel;
        getPropertyChangeSupport().firePropertyChange(PROP_GEL, old, this.gel);
    }

    public static final String PROP_SPOTANNOTATIONS = "spotAnnotations";
    private List<SpotAnnotation> spotAnnotations = new ActivatableArrayList<SpotAnnotation>();

    public List<SpotAnnotation> getSpotAnnotations() {
        activate(ActivationPurpose.READ);
        return spotAnnotations;
    }

    public void setSpotAnnotations(List<SpotAnnotation> spotAnnotations) {
        activate(ActivationPurpose.WRITE);
        List<SpotAnnotation> old = this.spotAnnotations;
        this.spotAnnotations = spotAnnotations;
        getPropertyChangeSupport().firePropertyChange(PROP_SPOTANNOTATIONS, old, this.spotAnnotations);
    }

}
