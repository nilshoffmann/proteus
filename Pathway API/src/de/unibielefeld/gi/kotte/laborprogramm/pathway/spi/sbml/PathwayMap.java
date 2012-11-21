package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi.sbml;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMap;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.sbml.jsbml.SBMLDocument;

public class PathwayMap implements IPathwayMap, Activatable {

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
    private SBMLDocument document;
    private String organismID;
    private String organismName;
    private Map<String, Pathway> pathways = new HashMap<String, Pathway>();
    public static final String PROP_ORG_ID = "organism ID";
    public static final String PROP_ORG_NAME = "organism name";
    public static final String PROP_DOCUMENT = "Document";
    public static final String PROP_PATHWAYS = "Pathways";

    @Override
    public SBMLDocument getDocument() {
        activate(ActivationPurpose.READ);
        return document;
    }

    @Override
    public void setDocument(SBMLDocument document) {
        activate(ActivationPurpose.WRITE);
        SBMLDocument oldDocument = this.document;
        this.document = document;
        getPropertyChangeSupport().firePropertyChange(PROP_DOCUMENT, oldDocument, document);
    }

    @Override
    public String getOrganismID() {
        activate(ActivationPurpose.READ);
        return organismID;
    }

    @Override
    public void setOrganismID(String organismID) {
        activate(ActivationPurpose.WRITE);
        String oldId = this.organismID;
        this.organismID = organismID;
        getPropertyChangeSupport().firePropertyChange(PROP_ORG_ID, oldId, organismID);
    }

    @Override
    public String getOrganismName() {
        activate(ActivationPurpose.READ);
        return organismName;
    }

    @Override
    public void setOrganismName(String organismName) {
        activate(ActivationPurpose.WRITE);
        String oldName = this.organismName;
        this.organismName = organismName;
        getPropertyChangeSupport().firePropertyChange(PROP_ORG_NAME, oldName, organismName);
    }

    @Override
    public void addPathway(Pathway pathway) {
        activate(ActivationPurpose.WRITE);
        pathways.put(pathway.getFrameid(), pathway);
        getPropertyChangeSupport().firePropertyChange(PROP_PATHWAYS, pathways, pathways);
    }

    @Override
    public Pathway getPathway(String frameID) {
        activate(ActivationPurpose.READ);
        return pathways.get(frameID);
    }

    @Override
    public Collection<Pathway> getPathways() {
        activate(ActivationPurpose.READ);
        return pathways.values();
    }
}
