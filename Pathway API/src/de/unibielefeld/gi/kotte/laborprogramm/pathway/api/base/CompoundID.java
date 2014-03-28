package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base;

/**
 * Defines the type of an ID Strign to identify a compound.
 *
 * @author Konstantin Otte
 */
public enum CompoundID {

    /**
     * The corresponding ID String is a CAS number.
     */
    CAS,
    /**
     * The corresponding ID String is a KEGG compound ID.
     */
    KEGG;
}
