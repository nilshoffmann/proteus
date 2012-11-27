package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMap;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.UUID;
import org.sbml.jsbml.SBMLDocument;

/**
 * Default implementation of IPathwayProject.
 *
 * @author kotte
 */
public class PathwayProject implements IPathwayProject, Activatable {

    /**
     * PropertyChangeSupport ala JavaBeans(tm) Not persisted!
     */
    private transient PropertyChangeSupport pcs = null;
    private Object activeProject;

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
    private String name;
    private SBMLDocument document;
    private IPathwayMap pathwayMap;
    public final static String PROP_NAME = "Pathway Project name";
    public final static String PROP_PATHWAY_MAP = "Pathway Map";
    public final static String PROP_DOCUMENT = "SBML Document";

    @Override
    public String getName() {
        activate(ActivationPurpose.READ);
        return name;
    }

    @Override
    public void setName(String name) {
        activate(ActivationPurpose.WRITE);
        String oldName = this.name;
        this.name = name;
        getPropertyChangeSupport().firePropertyChange(PROP_NAME, oldName, name);
    }

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
    public IPathwayMap getPathwayMap() {
        activate(ActivationPurpose.READ);
        return pathwayMap;
    }

    @Override
    public void setPathwayMap(IPathwayMap pathwayMap) {
        activate(ActivationPurpose.WRITE);
        IPathwayMap oldPathwayMap = this.pathwayMap;
        this.pathwayMap = pathwayMap;
        getPropertyChangeSupport().firePropertyChange(PROP_PATHWAY_MAP, oldPathwayMap, pathwayMap);
    }
}
