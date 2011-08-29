package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;

/**
 * Identification of a protein.
 *
 * @author kotte, hoffmann
 */
public interface IIdentification extends IPropertyChangeSource {

    public static final String PROPERTY_ACCESSION = "accession";
    public static final String PROPERTY_ABBREVIATION = "abbreviation";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_KEGG = "Kegg number";
    public static final String PROPERTY_GENDB_ID = "GenDB-ID";
    public static final String PROPERTY_PROTEIN_MW = "protein molecular weight";
    public static final String PROPERTY_PI_VALUE = "pi value";
    public static final String PROPERTY_COVERAGE = "MS coverage";
    public static final String PROPERTY_DIFFERENCE = "difference";
    public static final String PROPERTY_SCORE = "score";
    public static final String PROPERTY_METHOD = "method";

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
    public String getKeggNumber();
    public void setKeggNumber(String keggNumber);
    public float getPiValue();
    public void setPiValue(float piValue);
    public float getProteinMolecularWeight();
    public void setProteinMolecularWeight(float proteinMolecularWeight);
    public float getScore();
    public void setScore(float score);
    public String getMethod();
    public void setMethod(String method);

}