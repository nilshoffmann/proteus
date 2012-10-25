package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.CompoundID;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.ICompoundAnnotation;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathway;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation for ICompoundAnnotation
 *
 * @author kotte
 */
public class CompoundAnnotation implements ICompoundAnnotation {
    
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
        return pathways;
    }

    /**
     * Adds a pathway
     *
     * @param pathway new pathway
     */
    @Override
    public void addPathway(IPathway pathway) {
        pathways.add(pathway);
        propertyChangeSupport.firePropertyChange(PROP_PATHWAYS, pathways, pathways);
    }
    
    /**
     * Set the value of pathways
     *
     * @param pathways new value of pathways
     */
    @Override
    public void setPathways(List<IPathway> pathways) {
        List<IPathway> oldPathways = this.pathways;
        this.pathways = pathways;
        propertyChangeSupport.firePropertyChange(PROP_PATHWAYS, oldPathways, pathways);
    }
    
    /**
     * Gets the id Map.
     *
     * @return a ReactionID->String Map with identifications for the reaction
     */
    @Override
    public Map<CompoundID, String> getIdMap() {
        return id;
    }

    /**
     * Gets the id String for the specified id type.
     *
     * @return the value of id
     */
    @Override
    public String getId(CompoundID type) {
        return this.id.get(type);
    }
    
    /**
     * Checks wether there is an id String for the specified id type.
     *
     * @return true iff there is an id String for the specified id type
     */
    @Override
    public boolean hasId(CompoundID type) {
        return this.id.containsKey(type);
    }
    
    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    @Override
    public void addId(CompoundID type, String str) {
        id.put(type, str);
        propertyChangeSupport.firePropertyChange(PROP_ID, id, id);
    }
    
    /**
     * Get the value of compartment
     *
     * @return the value of compartment
     */
    @Override
    public String getCompartment() {
        return compartment;
    }

    /**
     * Set the value of compartment
     *
     * @param compartment new value of compartment
     */
    @Override
    public void setCompartment(String compartment) {
        String oldCompartment = this.compartment;
        this.compartment = compartment;
        propertyChangeSupport.firePropertyChange(PROP_COMPARTMENT, oldCompartment, compartment);
    }
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
