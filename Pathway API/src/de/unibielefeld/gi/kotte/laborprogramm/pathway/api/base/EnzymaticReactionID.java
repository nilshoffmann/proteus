package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base;

/**
 * Defines the type of an ID String to identify a reaction.
 *
 * @author kotte
 */
public enum EnzymaticReactionID {

    /**
     * The corresponding ID String is an EC number.
     */
    EC,
    /**
     * The corresponding ID String is a KEGG reaction id.
     */
    KEGG;
}
