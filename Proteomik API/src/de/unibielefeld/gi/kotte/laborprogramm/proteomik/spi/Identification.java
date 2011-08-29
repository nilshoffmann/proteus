package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Default implementation of IIdentification
 *
 * @author kotte, hoffmann
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
     *
     */
    private String accession;
    private String abbreviation;
    private String name;
    private String keggNumber = "0.0.0.0";
    private int gendbId = -1;
    private float proteinMolecularWeight = Float.NaN;
    private float piValue = Float.NaN;
    private int coverage = -1;
    private int difference = -1;
    private float score = Float.NaN;
    private String method;

    @Override
    public String getAbbreviation() {
        activate(ActivationPurpose.READ);
        return abbreviation;
    }

    @Override
    public void setAbbreviation(String abbreviation) {
        activate(ActivationPurpose.WRITE);
        this.abbreviation = abbreviation;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_ABBREVIATION, null, abbreviation);
    }

    @Override
    public String getAccession() {
        activate(ActivationPurpose.READ);
        return accession;
    }

    @Override
    public void setAccession(String accession) {
        activate(ActivationPurpose.WRITE);
        this.accession = accession;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_ACCESSION, null, accession);
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
    public int getCoverage() {
        activate(ActivationPurpose.READ);
        return coverage;
    }

    @Override
    public void setCoverage(int coverage) {
        activate(ActivationPurpose.WRITE);
        this.coverage = coverage;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_COVERAGE, null, coverage);
    }

    @Override
    public int getDifference() {
        activate(ActivationPurpose.READ);
        return difference;
    }

    @Override
    public void setDifference(int difference) {
        activate(ActivationPurpose.WRITE);
        this.difference = difference;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_DIFFERENCE, null, difference);
    }

    @Override
    public int getGendbId() {
        activate(ActivationPurpose.READ);
        return gendbId;
    }

    @Override
    public void setGendbId(int gendbId) {
        activate(ActivationPurpose.WRITE);
        this.gendbId = gendbId;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_GENDB_ID, null, gendbId);
    }

    @Override
    public String getKeggNumber() {
        activate(ActivationPurpose.READ);
        return keggNumber;
    }

    @Override
    public void setKeggNumber(String keggNumber) {
        activate(ActivationPurpose.WRITE);
        this.keggNumber = keggNumber;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_KEGG, null, keggNumber);
    }

    @Override
    public float getPiValue() {
        activate(ActivationPurpose.READ);
        return piValue;
    }

    @Override
    public void setPiValue(float piValue) {
        activate(ActivationPurpose.WRITE);
        this.piValue = piValue;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PI_VALUE, null, piValue);
    }

    @Override
    public float getProteinMolecularWeight() {
        activate(ActivationPurpose.READ);
        return proteinMolecularWeight;
    }

    @Override
    public void setProteinMolecularWeight(float proteinMolecularWeight) {
        activate(ActivationPurpose.WRITE);
        this.proteinMolecularWeight = proteinMolecularWeight;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PROTEIN_MW, null, proteinMolecularWeight);
    }

    @Override
    public float getScore() {
        activate(ActivationPurpose.READ);
        return score;
    }

    @Override
    public void setScore(float score) {
        activate(ActivationPurpose.WRITE);
        this.score = score;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_SCORE, null, score);
    }

    @Override
    public String getMethod() {
        activate(ActivationPurpose.READ);
        return method;
    }

    @Override
    public void setMethod(String method) {
        activate(ActivationPurpose.WRITE);
        this.method = method;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_METHOD, null, method);
    }

    @Override
    public String toString() {
        return "Identification{" + "accession=" + accession + "abbreviation=" + abbreviation + "name=" + name + "keggNumber=" + keggNumber + "gendbId=" + gendbId + "proteinMolecularWeight=" + proteinMolecularWeight + "piValue=" + piValue + "coverage=" + coverage + "difference=" + difference + "score=" + score + "method=" + method + '}';
    }
}
