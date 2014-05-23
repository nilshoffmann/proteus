package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import java.util.List;

/**
 * possible Identification of a protein.
 *
 * @author Konstantin Otte, hoffmann
 */
public interface IIdentification extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_ACCESSION = "accession";
    public static final String PROPERTY_ABBREVIATION = "abbreviation";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_KEGG = "Kegg number";
    public static final String PROPERTY_EC_NUMBER = "EC number";
    public static final String PROPERTY_GENDB_ID = "GenDB-ID";
    public static final String PROPERTY_GENDB_PROJECT = "GenDB-Project";
    public static final String PROPERTY_PROTEIN_MW = "protein molecular weight";
    public static final String PROPERTY_PI_VALUE = "pi value";
    public static final String PROPERTY_COVERAGE = "MS coverage";
    public static final String PROPERTY_DIFFERENCE = "difference";
    public static final String PROPERTY_SCORE = "score";
    public static final String PROPERTY_METHOD = "method";
    public static final String PROPERTY_SOURCE = "source";

    public String getAbbreviation();
    public void setAbbreviation(String abbreviation);
    public String getAccession();
    public void setAccession(String accession);
    public String getName();
    public void setName(String name);
    public int getCoverage();
    public void setCoverage(int coverage);
    public int getDifference();
    public void setDifference(int difference);
    public int getGendbId();
    public void setGendbId(int gendbId);
    public String getGendbProject();
    public void setGendbProject(String gendbProject);
    @Deprecated
    public List<String> getKeggNumbers();
    @Deprecated
    public void addKeggNumber(String keggNumber);
    @Deprecated
    public void setKeggNumbers(List<String> keggNumbers);
    public List<String> getEcNumbers();
    public void addEcNumber(String ecNumber);
    public void setEcNumbers(List<String> ecNumbers);
    public float getPiValue();
    public void setPiValue(float piValue);
    public float getProteinMolecularWeight();
    public void setProteinMolecularWeight(float proteinMolecularWeight);
    public float getScore();
    public void setScore(float score);
    public IIdentificationMethod getMethod();
    public void setMethod(IIdentificationMethod method);
    public String getSource();
    public void setSource(String source);

}