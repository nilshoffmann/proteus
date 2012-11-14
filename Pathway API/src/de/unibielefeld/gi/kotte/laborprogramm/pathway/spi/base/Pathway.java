package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi.base;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base.IPathway;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.UUID;

/**
 * Default implementation for IPathway.
 *
 * @author kotte
 */
public class Pathway implements IPathway, Activatable {

    /**
     * PropertyChangeSupport ala JavaBeans(tm) Not persisted!
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
    private UUID objectId = UUID.randomUUID();

    @Override
    public UUID getId() {
        activate(ActivationPurpose.READ);
        return objectId;
    }
    /**
     * Object definition
     */
    private String id;
    private String name;
    private List<String> synonyms;
    public static final String PROP_NAME = "name";
    public static final String PROP_ID = "Metacyc FrameId";
    public static final String PROP_SYNONYMS = "synonyms";

    public void setId(String id) {
        activate(ActivationPurpose.WRITE);
        String oldId = this.id;
        this.id = id;
        getPropertyChangeSupport().firePropertyChange(PROP_ID, oldId, id);
    }

    public void setName(String name) {
        activate(ActivationPurpose.WRITE);
        String oldName = this.name;
        this.name = name;
        getPropertyChangeSupport().firePropertyChange(PROP_NAME, oldName, name);
    }

    public void setSynonyms(List<String> synonyms) {
        activate(ActivationPurpose.WRITE);
        List<String> oldSynonyms = this.synonyms;
        this.synonyms = synonyms;
        getPropertyChangeSupport().firePropertyChange(PROP_SYNONYMS, oldSynonyms, synonyms);
    }

    @Override
    public String getMetacycId() {
        activate(ActivationPurpose.READ);
        return id;
    }

    @Override
    public String getName() {
        activate(ActivationPurpose.READ);
        return name;
    }

    @Override
    public List<String> getSynonyms() {
        activate(ActivationPurpose.READ);
        return synonyms;
    }
}
