package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi.base;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base.CompoundID;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base.ICompound;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base.IPathway;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Default implementation for ICompoundAnnotation
 *
 * @author kotte
 */
public class Compound implements ICompound, Activatable {

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
    private String compartment;
    private Map<CompoundID, String> id = new EnumMap<CompoundID, String>(CompoundID.class);
    private List<IPathway> pathways;
    public static final String PROP_PATHWAYS = "pathways";
    public static final String PROP_COMPARTMENT = "compartment";
    public static final String PROP_ID = "id";

    /**
     * Get the value of pathways
     *
     * @return the value of pathways
     */
    @Override
    public List<IPathway> getPathways() {
        activate(ActivationPurpose.READ);
        return pathways;
    }

    /**
     * Adds a pathway
     *
     * @param pathway new pathway
     */
    @Override
    public void addPathway(IPathway pathway) {
        activate(ActivationPurpose.WRITE);
        pathways.add(pathway);
        getPropertyChangeSupport().firePropertyChange(PROP_PATHWAYS, pathways, pathways);
    }

    /**
     * Set the value of pathways
     *
     * @param pathways new value of pathways
     */
    @Override
    public void setPathways(List<IPathway> pathways) {
        activate(ActivationPurpose.WRITE);
        List<IPathway> oldPathways = this.pathways;
        this.pathways = pathways;
        getPropertyChangeSupport().firePropertyChange(PROP_PATHWAYS, oldPathways, pathways);
    }

    /**
     * Gets the id Map.
     *
     * @return a ReactionID->String Map with identifications for the reaction
     */
    @Override
    public Map<CompoundID, String> getIdMap() {
        activate(ActivationPurpose.READ);
        return id;
    }

    /**
     * Gets the id String for the specified id type.
     *
     * @return the value of id
     */
    @Override
    public String getId(CompoundID type) {
        activate(ActivationPurpose.READ);
        return this.id.get(type);
    }

    /**
     * Checks wether there is an id String for the specified id type.
     *
     * @return true iff there is an id String for the specified id type
     */
    @Override
    public boolean hasId(CompoundID type) {
        activate(ActivationPurpose.READ);
        return this.id.containsKey(type);
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    @Override
    public void addId(CompoundID type, String str) {
        activate(ActivationPurpose.WRITE);
        id.put(type, str);
        getPropertyChangeSupport().firePropertyChange(PROP_ID, id, id);
    }

    /**
     * Get the value of compartment
     *
     * @return the value of compartment
     */
    @Override
    public String getCompartment() {
        activate(ActivationPurpose.READ);
        return compartment;
    }

    /**
     * Set the value of compartment
     *
     * @param compartment new value of compartment
     */
    @Override
    public void setCompartment(String compartment) {
        activate(ActivationPurpose.WRITE);
        String oldCompartment = this.compartment;
        this.compartment = compartment;
        getPropertyChangeSupport().firePropertyChange(PROP_COMPARTMENT, oldCompartment, compartment);
    }
}
